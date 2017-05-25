using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Assignement3
{
    /// <summary>
    /// This class calculates stores data for estimating the
    /// fuel consumption in different metrics and the cost per km
    /// </summary>
    class FuelCalculator
    {
        private double currReading;
        private double prevReading;
        private double fuelAmount;
        private double unitPrice;

        public FuelCalculator()
        {
            currReading = 0.0;
            prevReading = 0.0;
            fuelAmount = 0.0;
            unitPrice = 0.0;
        }

        /// <summary>
        /// Getter method related to the field name. This method
        /// allows other objects to get the value of the private field name.
        /// </summary>
        /// <returns>the values stored in the field name</returns>
        /// <remarks> The method needs no input from the caller</remarks>
        public double GetCurrentReading()
        {
            return currReading;
        }
        public double GetFuelAmount()
        {
            return fuelAmount;
        }
        public double GetPreviousReading()
        {
            return prevReading;
        }
        public double GetUnitPrice()
        {
            return unitPrice;
        }
        /// <summary>
        /// Setter method related to the private field name. This 
        /// method allows other objects to store a new value in the
        /// field but under controlled conditions. The method checks
        /// so the new value is a valid value and if not, it simply
        /// ignores the call, avoiding unwanted changes.
        /// </summary>
        /// <param newValue="newValue">the new value to be stored in the field</param>
        /// <remarks>This method needs an input parameter to receive the 
        /// argument. It does not need to return any value and therefore it
        /// is declared as void.</remarks>
        public void SetCurrentReading(double newValue)
        {
            if (newValue >=0)
                this.currReading = newValue;       
        }
        public void SetFuelAmount(double newValue)
        {
            if (newValue >= 0)
                this.fuelAmount = newValue;
        }
        //The previous reading should be equal or greater than zero (not negative).
        public void SetPreviousReading(double newValue)
        {
            if (newValue >= 0)
                this.prevReading = newValue;
        }
        public void SetUnitPrice(double newValue)
        {
            if (newValue >= 0)
                this.unitPrice = newValue;
        }
        /// <summary>
        /// Current odometer reading must be greater than the value of the previous reading.
        /// <returns>boolean value</returns>
        public bool ValidateOdometerValue()
        {
            if (currReading > prevReading)
            {
                return true;
            }
            else
            {
                MessageBox.Show("Current odometer value has to be greater than the previous one!");
                return false;
            }
        }
        /// <summary>
        /// Calculate the age at which one goes on pension.
        /// Calculation is based on the official pension age
        /// as defined above.
        /// </summary>
        /// <returns>the pension year.</returns>
        public double CalcConsumptionKilometerPerLiter()
        {
            double kmPerLit = 0.0;
            if (ValidateOdometerValue())
            {
                //kmPerLit = km/fuel amount
                kmPerLit = (currReading - prevReading) / fuelAmount;
                return kmPerLit;
            }
            else {
                return kmPerLit;
            }
        }
        public double CalcFuelConsumptionLiterPerKm()
        {
            double literPerKm = 0.0;
            if (ValidateOdometerValue())
            {
                //litPerKm = fuel amount / km;
                literPerKm = fuelAmount / (currReading - prevReading);
                return literPerKm;
            }
            else {
                return literPerKm;
            }
        }
        public double ConsumptionLiterPerMetricMile()
        {
            const double kmToMileFactor = 0.621371192;
            double litPerMetricMile = 0.0;
            double literPerKm = 0.0;

            if (ValidateOdometerValue())
            {
                //litPerKm = fuel amount / km;
                literPerKm = fuelAmount / (currReading - prevReading);
                //litPerMetricMile = litPerKm(as above) / kmToMileFactor
                litPerMetricMile = literPerKm / kmToMileFactor;
                return litPerMetricMile;
            }
            else {
                return litPerMetricMile;
            }
        }
        public double CalcFuelConsumptionPerSweMil()
        {
            double literPerSweMile = 0.0;
            if (ValidateOdometerValue())
            {
                //litPerKm = fuel amount / km;
                literPerSweMile = (fuelAmount / (currReading - prevReading))*10;
                return literPerSweMile;
            }
            else {
                return literPerSweMile;
            }
        }
        public double CalcCostPerKm()
        {
            double literPerKm = 0.0;
            double unitPrice = 0.0;
            if (ValidateOdometerValue())
            {
                //litPerKm = fuel amount / km;
                literPerKm = fuelAmount / (currReading - prevReading);
                unitPrice = literPerKm * unitPrice;
                return unitPrice;
            }
            else {
                return unitPrice;
            }
        }
    }
}
