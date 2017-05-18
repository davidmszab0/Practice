using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Assignement6
{
    class ContactManager
    {
        private List<Contact> contactManagerList;
        // ---------------- Constructors --------------
        public ContactManager()
        {
            contactManagerList = new List<Contact>();
        }
        // ---------------- Properties ------------------
        // returns the lenght of the List array
        public int Count
        {
            get { return contactManagerList.Count; }
        }
        // get the contact at the given position
        public Contact GetContact(int index)
        {
            if (IsIndexValid(index))
            {
                return contactManagerList[index];
            }
            return null;
        }
        // ---------------- Methods ------------------

        //Write an init() method
        public void Add(Contact contactObj)
        {
            if (contactObj != null)  //Important - the object must be created (in calling method)
                contactManagerList.Add(contactObj);
        }

        public Contact GetCopyElementAtPosition(int index)
        {
            if (IsIndexValid(index))
            {
                Contact origObj = contactManagerList[index];

                //Use the copy constructor of the Contact class
                Contact copyObj = new Contact(origObj);
                return copyObj;
            }
            else
                return null;
        }
        public bool IsIndexValid(int index)
        {
            return ((index >= 0) && (index < contactManagerList.Count));
        }
        public void DeleteContact(int index)
        {
            if (IsIndexValid(index))
            {
                // use the default method in the List class
                contactManagerList.RemoveAt(index);
            }
        }
    }
}
