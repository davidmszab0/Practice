using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Assignement4
{
    public partial class MainForm : Form
    {
        private Task currTask;
        // constructor for the Form
        public MainForm()
        {
            InitializeComponent();
            InitializeGUI();
        }
        private void InitializeGUI()
        {
            this.Text = "To Do Reminder" + "David Szabo";
            currTask = new Task(DateTime.Now);

            // combo box
            cmbPriority.Items.Clear();
            cmbPriority.Items.AddRange(Enum.GetNames(typeof(PriorityType)));
            cmbPriority.SelectedIndex = (int)PriorityType.Normal;

            // list tasks
            lstTasks.Items.Clear();
            lblClock.Text = String.Empty;
            // we have a clock in the right lower corner
            clockTimer.Start();

            txtDescription.Text = String.Empty;

            // dateTimePicker is where you choose the date
            dateTimePicker1.Format = DateTimePickerFormat.Custom;
            dateTimePicker1.CustomFormat = "yyyy-MM-dd     HH:mm";

            // toolTip is the hover text
            toolTip1.ShowAlways = true;
            toolTip1.SetToolTip(dateTimePicker1, "Click me to open calendar or write time here.");
        }
        //1. Check if description is given
        //2. if yes, save description, date and priorityValues in currTask
        private bool ReadAndValidateInput()
        {
            if (string.IsNullOrEmpty(txtDescription.Text))
            {
                MessageBox.Show("description is empty", "Error");
                return false;
            } else
            {
                // setting the values
                currTask.TaskDate = dateTimePicker1.Value; 
                currTask.Priority = (PriorityType)cmbPriority.SelectedIndex;
                currTask.Description = txtDescription.Text;
                return true;
            }
        }
        private void cmbPriority_SelectedIndexChanged(object sender, EventArgs e)
        {
            //Which option is selected by the user
            PriorityType prio = (PriorityType)cmbPriority.SelectedIndex;
        }

        private void btnAdd_Click(object sender, EventArgs e)
        {
            // validate and set the Task object variables, then add the changed variables to the Task obj
            if (ReadAndValidateInput())
            {
                lstTasks.Items.Add(currTask.ToString());
            }
        }
           // change the field values, click on an item and click the button. The method updates the Task obj and the list Box
        private void btnChange_Click(object sender, EventArgs e)
        {
            int index = lstTasks.SelectedIndex;
            // index has to be bigger than -1, so taht there are elements in the List Box
            if (index >= 0)
            {
                // set the Task data and validate it
                if (ReadAndValidateInput())
                    {
                        // remove item at the given index
                        lstTasks.Items.RemoveAt(index);
                        // add the fetched Task data
                        lstTasks.Items.Insert(index, currTask.ToString());
                    }
            } else
            {
                MessageBox.Show("Select an item from the ListBox!", "Error");
            }
        }
        // deleting a Task item
        private void btnDelete_Click(object sender, EventArgs e)
        {
            DialogResult dlg = MessageBox.Show("Are you sure to delete?", "Confirmation", MessageBoxButtons.YesNo, MessageBoxIcon.Warning);

            if (dlg == DialogResult.Yes)
            {
                int index = lstTasks.SelectedIndex;
                // index has to be bigger than -1, so taht there are elements in the List Box
                if (index >= 0)
                {
                    // remove item at the given index
                    lstTasks.Items.RemoveAt(index);
                }
            } else
            {
                Console.WriteLine("no delete");
            }
        }

        private void menuFileNew_Click(object sender, EventArgs e)
        {
            InitializeGUI();
        }
        private void menuFileExit_Click(object sender, EventArgs e)
        {
            DialogResult dlg = MessageBox.Show("Are you sure to exit?", "Confirmation", MessageBoxButtons.YesNo, MessageBoxIcon.Warning);

            if (dlg == DialogResult.Yes)
            {
                this.Close();
            }
            else
            {
                Console.WriteLine("no exit");
            }
        }

        private void menuHelpAbout_Click(object sender, EventArgs e)
        {
            // reference -> AboutBox1.cs file and the Properties/AssemblyInfo.cs file
            AboutBox1 dlgAbout = new AboutBox1();
            dlgAbout.ShowDialog();
        }
        //update the clock
        private void clockTimer_Tick(object sender, EventArgs e)
        {
            lblClock.Text = DateTime.Now.ToLongTimeString();
        }
    }
}
