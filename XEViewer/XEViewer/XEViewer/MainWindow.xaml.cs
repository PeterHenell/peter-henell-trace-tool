using System;
using System.Data.SqlClient;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using XEViewer.ViewModels;

namespace XEViewer
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        private MainWindowViewModel vm;
        public MainWindow()
        {
            Loaded += MainWindow_Loaded;
            InitializeComponent();
        }

        void MainWindow_Loaded(object sender, RoutedEventArgs e)
        {
            //XEManager manager = new XEManager();
            //var dt = manager.GetEvents(@"Data Source = (SEK-XFTRNR1); Initial Catalog = master; Integrated Security = SSPI", "Queryplan_Collector");
            //eventGrid.DataContext = dt;
            //foreach (var d in dt)
            //{
            //    Console.WriteLine(d);
            //}
            this.vm = new MainWindowViewModel();
            this.DataContext = vm; 
        }

      
    }
}
