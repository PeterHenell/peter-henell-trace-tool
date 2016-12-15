using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Data.SqlClient;
using Microsoft.SqlServer.Management.XEvent;
using Microsoft.SqlServer.Management.Sdk.Sfc;
// Create references to the following files for this code to work.
//
// C:\Program Files (x86)\Microsoft SQL Server\110\SDK\Assemblies\Microsoft.SqlServer.XEvent.dll
// C:\Program Files (x86)\Microsoft SQL Server\110\SDK\Assemblies\Microsoft.SqlServer.XEventEnum.dll
// C:\Program Files (x86)\Microsoft SQL Server\110\SDK\Assemblies\Microsoft.SqlServer.Management.Sdk.Sfc.dll
// C:\Program Files (x86)\Microsoft SQL Server\110\SDK\Assemblies\Microsoft.SqlServer.ConnectionInfo.dll

namespace XeOM_BlogDemo
{
    class XeObjectModel
    {
        static void Main(string[] args)
        {
            // Since all examples must connect to the server we'll
            // create a single XEStore object to use thorughout.
            XEStore xestore = ConnectToXEStore("localhost");

            //XESessionManager


            var session = xestore.Sessions.FirstOrDefault(x => x.Name == "Queryplan_Collector");
            
            foreach (var ev in session.Events)
            {
                foreach (var field in ev.EventFields)
                {
                    Console.WriteLine("{0}:{1}", field.Name, field.Value);
                }

            }

            foreach (Event evt in xestore.Sessions["Queryplan_Collector"].Events)
            {
                Console.WriteLine("\tEvent name: {0}", evt.Name);
            }

            //// Explore the Extended Event metadata.
            //XeExploreMetadata(xestore);

            //// Manipulate an event session.
            //XeSessionRuntime(xestore);

            Console.Write("Hit any key to exit.");
            Console.Read();
        }


        private static XEStore ConnectToXEStore(string server)
        {
            // The XEStore is a type of SFC store so we use the SqlStoreConnection
            // and a SqlConnection to pass to the XEStore.
            // Reference SFC and SqlServer.ConnectionInfo from SQL version specific dirtory,
            // eg: C:\Program Files (x86)\Microsoft SQL Server\110\SDK\Assemblies
            Microsoft.SqlServer.Management.Sdk.Sfc.SqlStoreConnection storeConnection;
            SqlConnectionStringBuilder conBuild = new SqlConnectionStringBuilder();
            conBuild.DataSource = string.Format("({0})", server);
            conBuild.InitialCatalog = "master";
            conBuild.IntegratedSecurity = true;

            storeConnection = new Microsoft.SqlServer.Management.Sdk.Sfc.SqlStoreConnection(
                new SqlConnection(conBuild.ConnectionString));

            return new XEStore(storeConnection);
        }

        #region XeExploreMetadata
        /// <summary>
        /// Driver for the exploration of the metadata object model.
        /// </summary>
        /// <param name="xestore">Accepts an XEStore object to explore.</param>
        private static void XeExploreMetadata(XEStore xestore)
        {
            ListPackages(xestore);
            ListEvents(xestore);
            ListSqlStmtEventFields(xestore);
        }

        /// <summary>
        /// Package is the top object for the metadat side of the object model. You can access
        /// all other objects through the package.
        /// 
        /// ListPackages uses the Packages collection of the XEStore to list out all the packages
        /// available in the store. For SQL Server this collection produces equivalent results
        /// to what is returned from dm_xe_packages.
        /// </summary>
        /// <param name="xestore">The XEStore for the server being explored.</param>
        private static void ListPackages(XEStore xestore)
        {
            Console.WriteLine("Packages registered on the server.");

            foreach (Package pkg in xestore.Packages)
            {
                Console.WriteLine("\tPackage Name: {0}\tGUID: {1}", pkg.Name, pkg.ID);
            }
            Console.WriteLine("Hit enter to continue.");
            Console.Read();
            Console.WriteLine();
        }

        /// <summary>
        /// Use the EventInfo collection of each Package object to list all the events known
        /// to the system. For SQL Server this porduces results equivalent to what is
        /// returned from dm_xe_objects where object_type = 'event'
        /// 
        /// The same pattern can be used for any object in the package via the collections:
        ///     ActionInfo (object_type = 'action')
        ///     TargetInfo (object_type = 'target')
        ///     PredSourceInfo (object_type = 'pred_source')
        ///     PredCompareInfo (object_type = 'pred_compare')
        ///     TypeInfo (object_type = 'type')
        ///     MapInfo (object_type = 'map')
        /// </summary>
        /// <param name="xestore"></param>
        private static void ListEvents(XEStore xestore)
        {
            foreach (Package pkg in xestore.Packages)
            {
                Console.WriteLine("List of events in {0} package.", pkg.Name);
                foreach (EventInfo evt in pkg.EventInfoSet)
                {
                    Console.WriteLine("\t{0} - {1}", evt.Name, evt.Description);
                }
                Console.WriteLine("Hit enter to continue.");
                Console.Read();
                Console.WriteLine();
            }
        }

        /// <summary>
        /// Event fields are slightly complex because there are three different types of fields that can exist
        /// in any event, readonly, data and customizable. The results of this procedure are equivalent to querying
        /// dm_xe_object_columns where object_name = 'sql_statement_completed' The individual type of field is shown
        /// in the column_type field of that table.
        /// 
        /// ReadOnlyEventColumnInfoSet - column_type = 'readonly'
        /// DataEventColumnInfoSet - column_type = 'data'
        /// EventColumnInfoSet - column_type = 'customizable'
        /// 
        /// Target fields would be accessed in a similar maner but by using the TargetColumnInfoSet object
        /// off the TargetInfo object.
        /// </summary>
        private static void ListSqlStmtEventFields(XEStore xestore)
        {
            Console.WriteLine("List the fields in the sql_statement_completed event.");

            EventInfo evt = xestore.ObjectInfoSet.Get<EventInfo>("sqlserver.sql_statement_completed");
            // Readonly columns
            foreach (ReadOnlyEventColumnInfo col in evt.ReadOnlyEventColumnInfoSet)
            {
                Console.WriteLine("\t{0}", col.Name);
            }
            // Data columns
            foreach (DataEventColumnInfo col in evt.DataEventColumnInfoSet)
            {
                Console.WriteLine("\t{0}", col.Name);
            }
            // Customizable columns
            foreach (EventColumnInfo col in evt.EventColumnInfoSet)
            {
                Console.WriteLine("\t{0}", col.Name);
            }
            Console.WriteLine("Hit enter to continue.");
            Console.Read();
            Console.WriteLine();
        }
        #endregion

        #region XeSessionRuntime
        /// <summary>
        /// Driver for the exploration of runtime object model.
        /// </summary>
        /// <param name="xestore">Accepts an XEStore object that will be used to create and manipulate sessions.</param>
        private static void XeSessionRuntime(XEStore xestore)
        {
            ListSession(xestore);
            ListSystemHealthEvents(xestore);
            ListSystemHealthTargets(xestore);
            ListWaitInfoActions(xestore);
            ListWaitInfoPredicate(xestore);
            CreateStatementTrackingSession(xestore);
        }

        /// <summary>
        /// Session is the top level object for access to the configured sessions and runtime state
        /// of event sessions on a server. Everything can be accessed using the Sessions collection
        /// of the XEStore object associated with the server.
        /// 
        /// The list of sessions is equivalent to server_event_sessions and the Running state is
        /// determined using dm_xe_sessions.
        /// </summary>
        /// <param name="xestore"></param>
        private static void ListSession(XEStore xestore)
        {
            Console.WriteLine("Session list.");

            foreach (Session session in xestore.Sessions)
            {
                Console.WriteLine("\t{0}\tRunning: {1}.", session.Name, session.IsRunning.ToString());
            }
            Console.WriteLine("Hit enter to continue.");
            Console.Read();
            Console.WriteLine();
        }

        /// <summary>
        /// Uses the Events collection of an existing session to return a list of all
        /// the events associated with the session. The information is equivalent to
        /// that returned from server_event_session_events.
        /// 
        /// The system health session must exist for this function to work.
        /// </summary>
        private static void ListSystemHealthEvents(XEStore xestore)
        {
            Console.WriteLine("List of events in the system_health session.");

            foreach (Event evt in xestore.Sessions["system_health"].Events)
            {
                Console.WriteLine("\tEvent name: {0}", evt.Name);
            }
            Console.WriteLine("Hit enter to continue.");
            Console.Read();
            Console.WriteLine();
        }

        /// <summary>
        /// Uses the Targets collection of an existing session to return a list of all
        /// the targets associated with the session. For each Target, use the TargetFields
        /// collection to examine the configuration of the target. The information is
        /// equivalent to that returned from server_event_session_targets and
        /// server_event_session_fields repsepectively.
        /// 
        /// The system_health session must exist for this function to work.
        /// </summary>
        private static void ListSystemHealthTargets(XEStore xestore)
        {
            Console.WriteLine("List of targets in the system_health session.");

            foreach (Target targ in xestore.Sessions["system_health"].Targets)
            {
                Console.WriteLine("\tTarget name: {0}", targ.Name);
                foreach (TargetField config in targ.TargetFields)
                {
                    Console.WriteLine("\t\tField name: {0}\tConfigured value: {1}", config.Name, config.Value);
                }
            }
            Console.WriteLine("Hit enter to continue.");
            Console.Read();
            Console.WriteLine();
        }

        /// <summary>
        /// Uses the Actions collection of an Event to list all actions associated with that event. The results come from
        /// server_event_session_actions. You must specify the event name using the syntac package.event to get to the actions.
        /// 
        /// The system_health session must exist for this function to work.
        /// </summary>
        private static void ListWaitInfoActions(XEStore xestore)
        {
            Console.WriteLine("List of actions for the wait_info event in system_health.");

            foreach (Microsoft.SqlServer.Management.XEvent.Action act in xestore.Sessions["system_health"].Events["sqlos.wait_info"].Actions)
            {
                Console.WriteLine("\tAction name: {0}", act.Name);
            }
            Console.WriteLine("Hit enter to continue.");
            Console.Read();
            Console.WriteLine();
        }

        /// <summary>
        /// Lists the predicate for an Event. The result comes from the predicate field
        /// of the server_event_session_events catalog.
        /// 
        /// The system_health session must exist for this function to work.
        /// </summary>
        private static void ListWaitInfoPredicate(XEStore xestore)
        {
            Console.WriteLine("The predicate for the wait_info event in the system_health session.");

            Console.WriteLine("\tPredicate for wait_info: {0}", xestore.Sessions["system_health"].Events["sqlos.wait_info"].PredicateExpression);
            Console.WriteLine("Hit enter to continue.");
            Console.Read();
            Console.WriteLine();
        }

        /// <summary>
        /// Bringing it all together to create a new session on the server. This function
        /// builds a new session, adds an event with both an action and a predicate, adds a target and finally 
        /// creates the session on the server. It then starts, stops and drops the sessions; each time showing
        /// the changes by listing all the sessions of the server.
        /// </summary>
        private static void CreateStatementTrackingSession(XEStore xestore)
        {
            // Show list of starting session.
            ListSession(xestore);

            // Create a session object and set session options.
            Session demo = xestore.CreateSession("demo_blog_session");
            demo.MaxDispatchLatency = 20; // Set in seconds.
            demo.MaxMemory = 2048; // Set in KB
            demo.TrackCausality = true; // Turn on correlation tracking.

            // Add the sql_statement_completed event to the session and configure
            // the optional field named statement to be collected.
            Event evt = demo.AddEvent("sql_statement_completed");
            evt.EventFields["collect_statement"].Value = 1;

            // Add an action.
            evt.AddAction("session_id");

            // Add a predicate for this event.
            evt.PredicateExpression = @"duration > 5000";

            // Add a target to the session.
            Target targ = demo.AddTarget("ring_buffer");
            targ.TargetFields["max_memory"].Value = 2048;

            // Add the session to the XEStore.
            demo.Create();
            ListSession(xestore);

            // Start and Stop the session.
            demo.Start();
            ListSession(xestore);

            demo.Stop();
            ListSession(xestore);

            demo.Drop();
            ListSession(xestore);
        }


        #endregion

        

    }
}
