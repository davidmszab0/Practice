using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Assignement6
{
    class Address
    {
        private string city;
        private CountryType country;
        private string street;
        private string zipCode;
        // ---------------- Constructors ----------------
        // Chain calling the constructors from the most arguments to the least
        // initializing the values in the default constructor
        public Address():this(string.Empty, string.Empty, string.Empty)
        {
        }
        // initialize the countries! 
        public Address(string cityObj, string zipCodeObj, string streetObj):this(cityObj, zipCodeObj, streetObj, CountryType.Sweden)
        {
        }
        public Address(string cityObj, string zipCodeObj, string streetObj, CountryType countryObj)
        {
            this.city = cityObj;
            this.street = streetObj;
            this.zipCode = zipCodeObj;
            country = countryObj;
        }
        // copy constructor
        public Address(Address other)
        {
            this.street = other.street;
            this.zipCode = other.zipCode;
            this.city = other.city;
            this.country = other.country;
        }
        // ---------------- Properties ------------------
        public string City
        {
            get { return city; }
            set
            {
                if (!string.IsNullOrEmpty(value))
                    city = value;
            }
        }
        public CountryType Country
        {
            get { return country; }
            set
            {
                    country = value;
            }
        }
        public string Street
        {
            get { return street; }
            set
            {
                if (!string.IsNullOrEmpty(value))
                    street = value;
            }
        }
        public string ZipCode
        {
            get { return zipCode; }
            set
            {
                if (!string.IsNullOrEmpty(value))
                    zipCode = value;
            }
        }
        // ---------------- Methods ------------------
        // make a string out of the enum country
        private string GetCountryString()
        {
            return Country.ToString();
        }
        public override string ToString()
        {
            string strOut = string.Format("{0, -20}     {1, -8}     {2, -12}    {3, -12}", 
                street, zipCode, city, GetCountryString());
            return strOut;
        }
    }
}
