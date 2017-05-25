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
    public partial class MainForm : Form
    {
        private const int maxIngredients = 50;
        private const int maxRecipes = 200;
        private Recipe currentRecipe;
        private RecipeManager recipeMgr;
        public MainForm()
        {
            InitializeComponent();
            InitializeGUI();
        }
        private void InitializeGUI()
        {
            // storing data of a recipe progressed
            currentRecipe = new Recipe(maxIngredients);
            // handling all the recipes
            recipeMgr = new RecipeManager(maxRecipes);

            cmbCategory.Items.Clear();
            cmbCategory.Items.AddRange(Enum.GetNames(typeof(FoodCategory)));
            cmbCategory.SelectedIndex = (int)FoodCategory.Meat;

            lstRecipes.Items.Clear();

            txtRecipeName.Text = String.Empty;
            txtDescription.Text = String.Empty;
        }
        private void WriteOutPut()
        {
            // Read the values
            Console.WriteLine("write output");
            currentRecipe.Name = txtRecipeName.Text.Trim();
            currentRecipe.Category = (FoodCategory)cmbCategory.SelectedIndex;
            currentRecipe.Description = txtDescription.Text.Trim();

            // checks if current recipe being processed is not null, if there is a new Recipe it adds to the RecipeList[]
            recipeMgr.Add(currentRecipe);
            UpdateGUI();
            // this line took me 5 hours!!!!! 
            //you need to recreate the currentRecipe object so that it doesn't point to the same memory location
            currentRecipe = new Recipe(maxRecipes);
            // resetting the variables in the new Reciope obj
            currentRecipe.DefaultValues();
        }
        private void UpdateGUI()
        {
            // I want to add the recipeList to the list Box! 
            Console.WriteLine("update GUI");
            // I need a string[] fot hte listBox.Items.AddRange()
            string[] recipeListString = recipeMgr.RecipeListToString();
            lstRecipes.Items.Clear();
            lstRecipes.Items.AddRange(recipeListString);
        }
        private bool CheckInput()
        {
            bool ok = !string.IsNullOrEmpty(txtRecipeName.Text);
            ok = ok && !string.IsNullOrEmpty(txtDescription.Text);
            return ok;
        }
        private void btnAddRecipe_Click(object sender, EventArgs e)
        {
            if (!CheckInput())
            {
                Console.WriteLine("CheckInput is: " + CheckInput());
                return;
            } else
            {
                Console.WriteLine("CheckInput is: " + CheckInput());
            }
            WriteOutPut();
        }

        private void btnAddIngredient_Click(object sender, EventArgs e)
        {
            FormIngredient dlg = new FormIngredient(currentRecipe);
            DialogResult dlgResult =  dlg.ShowDialog();

            if (dlgResult == DialogResult.OK)
            {
                if (currentRecipe.GetCurrentNoOfIngredients() <= 0)
                {
                    MessageBox.Show("No ingredients specified!");
                    // don't crash if the user doesn't input an ingredient
                    recipeMgr.Add(currentRecipe);
                    UpdateGUI();
                }
            }
        }
    }
}
