   M S B A M L   `   `   `  ����  XEViewer    XEViewer.MainWindow   5   	   R NWindowsBase, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35W SPresentationCore, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35\ XPresentationFramework, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35D 9http://schemas.microsoft.com/winfx/2006/xaml/presentation    5   	   R NSystem.Xaml, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     5   	     � Title$  
MainWindow��5   	   $	��350��6   $	��525��6)   .��5      �  � RowDefinitions 5   
   �� 5      $	��10* �6   �� 5      $	��90* �6    � ColumnDefinitions 5
   
   �� 5      $��50 �6   �� 5      $	��90* �6   .+�5   
   �� $��0��6   $��1��6%   .+�5      �� $��Start��6   ��6%   �� *StartTraceCommand+�� 5      $
��Stop��6   ��6$   �� *StopTraceCommand+�� 5      $��Edit...��6   ��6'   �� *EditTraceCommand+5      �� 5   
   $��1��61   $��0��6A   ��6   �� *Items.Count+6A   &   System.Windows.Controls.DataGrid  5   
   -   5          Name$ 	eventGrid��5      $
��Left=�5       $	��Top=�5!      $��1��5"      $��1��6#   $
��Auto��5#      $
��Auto��6    $��Stretch=�5$      $��Stretch=�68      AutoGenerateColumns . ��5      �� *Items+5)      5+      5,      