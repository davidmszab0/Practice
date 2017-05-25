using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Assignement3
{
    class BMICalculator
    {
        private string name; //last and first
        private float height;
        private float weight;
        private double BMI;
        private string weightCategory;
        private string weightResults;

        public BMICalculator()
        {
            name = string.Empty;
            weightCategory = string.Empty;
            weightResults = string.Empty;
            height = 0.0F;
            weight = 0.0F;
            BMI = 0.0;
        }
        /// <summary>
        /// Getter method related to the field name. This method
        /// allows other objects to get the value of the private 
        /// field name.
        /// </summary>
        /// <returns>the values stored in the field name</returns>
        /// <remarks> The method needs no input from the caller. </remarks>
        public string GetName()
        {
            if (string.IsNullOrEmpty(name))
                return "The unknown!";
            else
                return name;
        }
        /// <summary>
        /// Setter method related to the private field name. This 
        /// method allows other objects to store a new value in the
        /// field but under controlled conditions. The method checks
        /// so the new value is a valid value and if not, it simply
        /// ignores the call, avoiding unwanted changes.
        /// </summary>
        /// <param name="name">the new value to be stored in the field</param>
        /// <remarks>This method needs an input parameter to receive the 
        /// argument. It does not need to return any value and therefore it
        /// is declared as void.</remarks>
        public void SetName(string name)
        {
            if (!string.IsNullOrEmpty(name))
                this.name = name;
        }
        public double GetHeight()
        {
            return height;
        }
        public double GetWeight()
        {
            return weight;
        }
        
        public void SetWeight(float newValue)
        {
            if (newValue >= 0)
                this.weight = newValue;
        }
        public void SetHeight(float newValue)
        {
            if (newValue >= 0)
                this.height = newValue;
        }
        public string GetWeightCategory()
        {
            if (string.IsNullOrEmpty(weightCategory))
                return "The unknown!";
            else
                return weightCategory;
        }
        public string GetWeightResults()
        {
            if (string.IsNullOrEmpty(weightResults))
                return "The unknown!";
            else
                return weightResults;
        }
        /// <summary>
        /// 
        /// </summary>
        /// <returns>the pension year.</returns>
        public double CalcBMI_Metric()
        {
            BMI = weight / (height/100 * height/100);

            return BMI;
        }
        public double CalcBMI_US()
        {
            BMI = 703 * weight / (height * height);

            return BMI;
        }
        public void WeightCategory()
        {
            if (BMI < 18.5)
            {
                this.weightCategory = "Underweigth";
                this.weightResults = "Underweight BMI is below 18.5";
            }
            else if (18.5 <= BMI && BMI <= 24.9)
            {
                this.weightCategory = "Normal weight";
                this.weightResults = "Normal BMI is between 18.5 and 24.9";
            }
            else if (25 <= BMI && BMI <= 29.9)
            {
                this.weightCategory = "Overweight (Pre-obesity)";
                this.weightResults = "Pre-obese BMI is between 25 and 29.9";
            }
            else if (30 <= BMI && 34.9 <= BMI)
            {
                this.weightCategory = "Obesity class I";
                this.weightResults = "Obese I. BMI is between 30 and 34.9";
            }
            else if (35 <= BMI && BMI <= 39.9)
            {
                this.weightCategory = "Obesity class II";
                this.weightResults = "Obese II. BMI is between 35 and 39.9";
            }
            else if (40 < BMI)
            {
                this.weightCategory = "Obesity class III";
                this.weightResults = "Obese III. BMI is above 40";
            }
        }
    }
}
