using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Assignement3
{
    public partial class MainForm : Form
    {
        private FuelCalculator carMilage;
        private BMICalculator bmiCalc;
        private string name = string.Empty;

        public MainForm()
        {
            InitializeComponent();

            InitializeGUI();
        }

        private void InitializeGUI()
        {
            // ---------------Fuel initialization ----------------
            // initialize a new FuelCalculator object
            carMilage = new FuelCalculator();
            // Change the lable text on the GUI initialization
            lblCostPerKm.Text = " -  ";
            lblFuelConsumptionKmLiter.Text = " -  ";
            lblFuelConsumptionLiterKm.Text = " -  ";
            lblFuelConsumptionLiterSweMile.Text = " -  ";
            lblFuelConsumptionLitMetric.Text = " -  ";


            // ---------------BMI initialization ----------------
            bmiCalc = new BMICalculator();
            lblBMI.Text = " -  ";
            lblWeightCat.Text = " -  ";
            lblBMIResult.Text = " -  ";
            radioBtnMetric.Checked = true;
            radioBtnUS.Checked = false;
        }

        private bool ReadInputFuel()
        {
            //Local variable to temporaily save the input
            double currReadingValue = 0;
            double prevReadingValue = 0;
            double fuelAmount = 0;
            double unitPrice = 0;

            //ok from the control of name can be true/false 
            //if true tryParse must also be true for currReadingValue, prevReadingValue, fuelAmount and unitPrice
            //out - says that the variables (e.g. currReadingValue) are output variables
            //intended to receive the converted value.  If TryParse fails, the variable
            //is set to zero. Control of the value of ok is important!
            bool ok = double.TryParse(txtCurrReading.Text, out currReadingValue);
            ok = ok && double.TryParse(txtPrevReading.Text, out prevReadingValue);
            ok = ok && double.TryParse(txtFuelAmount.Text, out fuelAmount);
            ok = ok && double.TryParse(txtPricePerLiter.Text, out unitPrice);

            if (ok)
            {
                //Call the setter methods to send the input to
                //carMilage object, FuelCalculator
                carMilage.SetPreviousReading(prevReadingValue);
                carMilage.SetCurrentReading(currReadingValue);
                carMilage.SetFuelAmount(fuelAmount);
                carMilage.SetUnitPrice(unitPrice);
            }
            return ok;
        }
        private void UpdateGUIFuel()
        {
            // ---------- Update Fuel Fields ---------------------
            lblFuelConsumptionKmLiter.Text = carMilage.CalcConsumptionKilometerPerLiter().ToString();
            lblFuelConsumptionLiterKm.Text = carMilage.CalcFuelConsumptionLiterPerKm().ToString();
            lblFuelConsumptionLiterSweMile.Text = carMilage.CalcFuelConsumptionPerSweMil().ToString();
            lblFuelConsumptionLitMetric.Text = carMilage.ConsumptionLiterPerMetricMile().ToString();
            lblCostPerKm.Text = carMilage.CalcCostPerKm().ToString();            
        }
        private void ClearTxtBoxes()
        {
            txtCurrReading.Text = "";
            txtFuelAmount.Text = "";
            txtPrevReading.Text = "";
            txtPricePerLiter.Text = "";

            //--- BMI related ---
            txtNameBMI.Text = "";
            txtHeight.Text = "";
            txtWeight.Text = "";
        }
        private void btnCalcFuel_Click(object sender, EventArgs e)
        {
            //if ReadInput is done successfully, ok will be true
            bool ok = ReadInputFuel();

            //Continue only if the input process is successful.
            if (ok)
            {
                UpdateGUIFuel();
                ClearTxtBoxes();
            }
            else
            {
                MessageBox.Show("Invalid input!");
                //return;   //cancel rest of execution (if you have more code after this block.
            }
        }
        private bool ReadBMIInput()
        {
            float height = 0.0F;
            float weigth = 0.0F;
            ReadName();
            
            bool ok =  float.TryParse(txtHeight.Text, out height);
            ok = ok && float.TryParse(txtWeight.Text, out weigth);

            if (ok)
            {
                //Call the setter methods to send the input to
                //carMilage object, FuelCalculator
                bmiCalc.SetName(name);
                bmiCalc.SetHeight(height);
                bmiCalc.SetWeight(weigth);
            }
            return ok;
        }
        private void ReadName()
        {
            name = txtNameBMI.Text.Trim();  //trim beginning and trailing spaces
            if (!string.IsNullOrEmpty(name))
                name = txtNameBMI.Text;
            else
                name = "No Name";
        }
        private void btnCalcBMI_Click(object sender, EventArgs e)
        {
            //if ReadInput is done successfully, ok will be true
            bool ok = ReadBMIInput();

            //Continue only if the input process is successful.
            if (ok)
            {
                UpdateGUIBMI();
                ClearTxtBoxes();
            }
            else
            {
                MessageBox.Show("Invalid input!");
                //return;   //cancel rest of execution (if you have more code after this block.
            }
        }
        private void UpdateGUIBMI()
        {
         // ----------- Update BMI Fields ---------------------
            grpBoxBMI.Text = bmiCalc.GetName().ToUpper();
            if (radioBtnMetric.Checked == true)
                lblBMI.Text = bmiCalc.CalcBMI_Metric().ToString();
            else if (radioBtnUS.Checked == true)
                lblBMI.Text = bmiCalc.CalcBMI_US().ToString();

            bmiCalc.WeightCategory();
            lblWeightCat.Text = bmiCalc.GetWeightCategory();
            lblBMIResult.Text = bmiCalc.GetWeightResults();
        }
    }
}
