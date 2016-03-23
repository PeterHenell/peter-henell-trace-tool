using Microsoft.SqlServer.Management.XEvent;
using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace XEViewer
{
    public class XESessionManager : IDisposable
    {
        private readonly XEStore xestore;

        public void Create(string server, Action<XESessionManager> create)
        {
            using (var session = new XESessionManager(server))
            {
                create(session);
            }
        }

        private XESessionManager(string server)
        {
            this.xestore = ConnectToXEStore(server);
        }

        public XESessionManager 

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
                new SqlConnection(conBuild.ConnectionString.ToString()));

            return new XEStore(storeConnection);
        }

        public void Dispose()
        {
            
        }
    }
}
