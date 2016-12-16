using GalaSoft.MvvmLight;
using GalaSoft.MvvmLight.Threading;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Threading;
using XESession.Lib;

namespace XEViewer.ViewModels
{
    public class MainWindowViewModel : ViewModelBase
    {
        private DataTable _table;
        //public DataTable Data
        //{
        //    get { return _table; }
        //    set
        //    {
        //        _table = value;
        //        RaisePropertyChanged("Data");
        //    }
        //}

        private System.Collections.ObjectModel.ObservableCollection<dynamic> _items;

        public System.Collections.ObjectModel.ObservableCollection<dynamic> Items
        {
            get { return _items; }
            set
            {
                _items = value;
                RaisePropertyChanged("Items");
            }
        }



        public MainWindowViewModel()
        {
            _table = new DataTable("Events");
            _table.Columns.Add("EventName", typeof(string));
            _table.Columns.Add("TimeStamp", typeof(DateTime));

            Items = new System.Collections.ObjectModel.ObservableCollection<dynamic>();

            BackgroundWorker worker = new BackgroundWorker();
            worker.DoWork += dowork;
            worker.RunWorkerAsync();
        }

        private void dowork(object sender, DoWorkEventArgs e)
        {
            XEManager manager = new XEManager();
            //while (true)
            //{
            DynamicTypeBuilder builder = null;
            foreach (var dr in manager.GetEvents(GetConnection(), "Queryplan_Collector", _table))
            {
                builder = builder ?? new DynamicTypeBuilder(_table);
                //_table.Rows.Add(dr);
                //var builder = new DynamicTypeBuilder(_table);
                var item = builder.CreateNewObject(_table);
                builder.SetValues(item, dr);
                DispatcherHelper.CheckBeginInvokeOnUI(() =>
                    {
                        // Dispatch back to the main thread
                        Items.Add(item);
                    });

                //Dispatcher.CurrentDispatcher.BeginInvoke((Action)(() =>
                //{

                //}));

            }
            //Thread.Sleep(1000);
            //}
        }

        private string GetConnection()
        {
            var builder = new SqlConnectionStringBuilder();
            builder.DataSource = "SEK-XFTRNR1";
            builder.InitialCatalog = "master";
            builder.IntegratedSecurity = true;
            return builder.ToString();
        }
    }
}
