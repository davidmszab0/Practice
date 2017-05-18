using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Assignement6
{
    public partial class MainForm : Form
    {
        /// <summary>
        /// MainForm is using ("has a") relation 
        /// It "has an" object of type ContactManager
        /// </summary>
        private ContactManager contactMgr = null;  //ref variable declared

        public MainForm()
        {
            InitializeComponent();

            // get the default values in the mainForms input fields
            InitializeGUI();

            //Initialize the manager, otherwise, you can't add/change/delete objects with it
            contactMgr = new ContactManager();
        }
        private void InitializeGUI()
        {
            // empties the text fields
            DefaultValues();

            // Fill the estate combobox with values from enum
            cmbCountries.Items.AddRange(Enum.GetNames(typeof(CountryType)));
            cmbCountries.SelectedIndex = (int)CountryType.Sweden; //Choose one type as default
        }
        private void DefaultValues()
        {
            //Clear output controls
            txtFirstName.Text = String.Empty;
            txtLastName.Text = String.Empty;

            txtCity.Text = String.Empty;
            txtStreet.Text = String.Empty;
            txtZipCode.Text = String.Empty;
        }
        private Address ReadAddress()
        {
            //Create a local address instance
            Address adr = new Address();

            // checking if the things inputted in the text fields are strings
            bool ok = !string.IsNullOrEmpty(txtStreet.Text);
            ok = ok && !string.IsNullOrEmpty(txtCity.Text);
            ok = ok && !string.IsNullOrEmpty(txtZipCode.Text);
            if (ok)
            {
                // if the text fields are Okay, they contain string, add them to the address object
                adr.Street = txtStreet.Text;
                adr.City = txtCity.Text;
                adr.ZipCode = txtZipCode.Text;
                adr.Country = (CountryType)cmbCountries.SelectedIndex;
            } else
            {
                MessageBox.Show("Address input was not correct");
            }
            return adr;  //return the object (containing address data)
        }
        private bool ReadInput(out Contact contact)
        {
            //Create a local estate instance for filling in input
            contact = new Contact();

            //Call a method that returns a complete address object 
            Address adr = ReadAddress();

            //Set the address in the object estate 
            contact.Address = adr;

            bool ok = !string.IsNullOrEmpty(txtFirstName.Text);
            ok = ok && !string.IsNullOrEmpty(txtLastName.Text);

            if (ok)
            {
                // if the textfields contain strings then add them to the contact object
                contact.FirstName = txtFirstName.Text;
                contact.LastName = txtLastName.Text;
            } else
            {
                MessageBox.Show("Contact input was not right");
            }
            return ok;
        }

        private void btnAdd_Click(object sender, EventArgs e)
        {
            Contact contact;

            // 'out' causes arguments to be passed by reference, variable doesnt have to be initialized
            if (ReadInput(out contact))
            {
                contactMgr.Add(contact);
                UpdateResults();
                DefaultValues();
            } else
            {
                return;
            }
        }
        private void UpdateResults()
        {
            lstResults.Items.Clear();  //Erase current list
            //Get one elemnet at a time from manager, and call its 
            //ToString method for info - send to listbox
            for (int index = 0; index < contactMgr.Count; index++)
            {
                // copy elemnet is better to avoid reference type errors!
                Contact contact = contactMgr.GetCopyElementAtPosition(index);
                lstResults.Items.Add(contact.ToString());
            }
            lblNoOfRecords.Text = contactMgr.Count.ToString();
        } 
        private void btnChange_Click(object sender, EventArgs e)
        {
            int index = lstResults.SelectedIndex;
            if (index < 0)
                return;

            // get the index of the element that we want to change
            Contact contact = contactMgr.GetContact(index);

            // change this element's values and add the changes to the contact object
            contact.Address.Country = (CountryType)cmbCountries.SelectedIndex;
            contact.FirstName = txtFirstName.Text;
            contact.LastName = txtLastName.Text;

            contact.Address.City = txtCity.Text;
            contact.Address.Street = txtStreet.Text;
            contact.Address.ZipCode = txtZipCode.Text;

            // make the results seeable instantly
            UpdateResults();
        }

        private void btnDelete_Click(object sender, EventArgs e)
        {
            int index = lstResults.SelectedIndex;
            // index has to be bigger than -1, so that there are elements in the List Box
            if (index >= 0)
            {
                // remove item at the given index
                contactMgr.DeleteContact(index);
                lstResults.Items.RemoveAt(index);
            } else
            {
                MessageBox.Show("There is nothing to delete");
            }
        }

        private void lstResults_SelectedIndexChanged(object sender, EventArgs e)
        {
            UpdateContactInfoFromRegistry();
        }
        // when clicking on a listBox item, update the textField with the corresponding values
        private void UpdateContactInfoFromRegistry()
        {
            int index = lstResults.SelectedIndex;
            if (index < 0)
                return;

            Contact contact = contactMgr.GetContact(index);

            cmbCountries.SelectedIndex = (int)contact.Address.Country;
            txtFirstName.Text = contact.FirstName;
            txtLastName.Text = contact.LastName;

            txtCity.Text = contact.Address.City;
            txtStreet.Text = contact.Address.Street;
            txtZipCode.Text = contact.Address.ZipCode;
        }
        // clear the text fields
        private void btnClear_Click(object sender, EventArgs e)
        {
            DefaultValues();
        }
    }
}
