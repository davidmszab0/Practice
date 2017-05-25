using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Assignement6
{
    class Contact
    {
        private Address address;
        private string firstName;
        private string lastName;
        private string fullName;
        // ---------------- Constructors --------------
        // Default Constructor
        public Contact()
        {
            this.firstName = string.Empty;
            this.lastName = string.Empty;
            //create member field
            address = new Address();
        }
        // copy constructor
        public Contact(Contact other)
        {
            this.firstName = other.firstName;
            this.lastName = other.lastName;
            //NOTE! The copy constructor of the address class is called
            //directly to copy the address. obj1 = obj2 does NOT provide a deep copy.
            //Here is one good example of using a copy constructor!
            this.address = new Address(other.address);
        }
        // ---------------- Properties ----------------
        public Address Address
        {
            get { return address; }
            set
            {
                    address = value;
            }
        }
        public string FirstName
        {
            get { return firstName; }
            set
            {
                if (!string.IsNullOrEmpty(value))
                    firstName = value;
            }
        }
        public string LastName
        {
            get { return lastName; }
            set
            {
                if (!string.IsNullOrEmpty(value))
                    lastName = value;
            }
        }
        public string FullName
        {
            get { return fullName; }
            set
            {
                if (!string.IsNullOrEmpty(value))
                    fullName = value;
            }
        }
        // ---------------- Methods ------------------
        // overriding the toString() in the Contacts class
        public override string ToString()
        {
            string strOut = string.Format("{0, -15} {1, -15}     {2, -60}",
                FirstName, LastName, address.ToString());
            return strOut;
        }
    }
}
