using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Assignement5
{
    public partial class FormIngredient : Form
    {
        /*
        The only instance variable the class needs is an object of Recipe – to receive data from the client
        class (FormMain) and to send back new or changed data to the client class.

        The client-class, MainForm can get data from this obj by set-properties and MainForm can send data to this object
        by initializing the constructor with a Recipe obj / or using a set property from the MainForm
        */
        private Recipe currentRecipe;

        public FormIngredient(Recipe recipe)
        {
            InitializeComponent();

            // both objects are pointing to the same memory location, they don't get copied, but if recipe obj changes, so does
            // currentRecipe
            currentRecipe = recipe;

            if (string.IsNullOrEmpty(recipe.Name))
                this.Text = "No Recipe Name";
            else
                this.Text = recipe.Name + "Add Ingredients";

            InitializeGUI();
        }
        private void InitializeGUI()
        {
            // make sure that the ingredientList is created in the object recipe
            if (currentRecipe.Ingredients == null)
                currentRecipe.Ingredients = new string[currentRecipe.MaxNoOfIngredients]; // property MaxNoOfIngredients

            lstIngredients.Items.Clear();
            // put a 0 to the label that counts the no of ingredients
            lblNoIngredients.Text = currentRecipe.GetCurrentNoOfIngredients().ToString();
        }
        // ---------------- Properties ------------------
        // not used, could be deleted
        public Recipe Recipe
        {
            get { return currentRecipe; }
            set
            {
                currentRecipe = value;
            }
        }
        // ---------------- Methods ------------------
        private void btnAddIngredient_Click_1(object sender, EventArgs e)
        {
            Console.WriteLine("add ingredient button");
            // add what is in the textBox to the Ingredients Array in the Recipe object
            currentRecipe.AddIngredient(txtIngredient.Text);
            // upddate the listBox
            lstIngredients.Items.Add(txtIngredient.Text);
            // update the label to get the current number of ingredients
            lblNoIngredients.Text = currentRecipe.GetCurrentNoOfIngredients().ToString();
        }
    }
}
