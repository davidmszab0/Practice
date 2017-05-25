using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

/*
    Recipe is a class that defines a recipe with name, category, ingredients and a description
    Saves data for a recipe as described above in an object (write the class Recipe).
*/
namespace Assignement5
{
    public class Recipe
    {
        private string name;
        private string[] ingredientsArray;
        private FoodCategory category;
        private string description;
        /*
            Every recipe has:
            • a list of ingredients (strings)
            • a description (instructions)
            • a category (use any grouping, ex Meat, Fish, Sea Food, Vegetarian).

            All the variables are stored on the Recipe objects
        */
        public Recipe(int maxIngredients)
        {
            ingredientsArray = new string[maxIngredients];
            DefaultValues();
        }
        // resetting the variables to the initialization state
        public void DefaultValues()
        {
            name = String.Empty;
            category = FoodCategory.Meat;
            description = String.Empty;

            for (int i = 0; i < ingredientsArray.Length; i++)
            {
                ingredientsArray[i] = String.Empty;
            }
        }
        // ---------------- Properties ------------------
        public string Name
        {
            get { return name; }
            set
            {
                if (!string.IsNullOrEmpty(value))
                    name = value;
            }
        }
        public string Description
        {
          get { return description; }
          set
            {
                if (!string.IsNullOrEmpty(value))
                    description = value;
            }
        }
        public FoodCategory Category
        {
            get { return category; }
            set
            {
                category = value;
            }
        }
        public string[] Ingredients
        {
            get { return ingredientsArray; }
            set {
                if (value != null)
                    ingredientsArray = value;
                }
        }
        public int MaxNoOfIngredients
        {
            get { return ingredientsArray.Length;  }
        }
        // ---------------- Methods ------------------
        /*
        FindVacantPosition: Write a method that loops through the array and returns the first vacant position. A vacant position is a position
        in which no element is saved, i.e. the value empty (according to above initialization) or null.
        Note: if no vacant position is found, return -1 from this method. The caller method can by checking against this value depict if the
        value could be saved in a position or if all positions were occupied.
        */
        private int FindVacantPosition()
        {
            for (int i = 0; i < ingredientsArray.Length; i++)
            {
                if (string.IsNullOrEmpty(ingredientsArray[i]))
                    return i;
            }
            return -1;
        }
        // add ingredient 
        /*
        AddIngredient: Write a method with a parameter for receiving a new ingredient string. Before adding, you should of course find a
        vacant element.
        Do the following in the code part: of this element:
        • Call the method FindVacantPosition and save the return value in a temporary variable (ex index) in the method.
        int index = FindVacantPosition();
        • If index >= 0, save the value (coming as argument) in the position index in the array and return true; otherwise, return false;
        Note: Instead of using the keyword return in several places in the same method, use a local variable and assign it a value depending
        on your conditions. Here,you can use a bool variable and assign it a true or false value and then return the variable at the end of
        the method.
            */
        public bool AddIngredient(string Value)
        {
            bool ok = false;

            int index = FindVacantPosition();
            if (0 <= index)
            {
                ingredientsArray[index] = Value;
                ok = true;
            }
            return ok;
        }
        /*
        CheckIndex: Write a method that returns true if a given index is within the range of the array. i.e. index should be greater or equal to
        0 and less than the array’s Length property. This method can be called from other methods in the class to make sure that the
        array is not indexed outside its range.
        */
        private bool CheckIndex(int index)
        {
            if (0 <= index && index < ingredientsArray.Length)
                return true;

            return false;
        }
        // get the current number of ingredients
        public int GetCurrentNoOfIngredients()
        {
            int count = 0;
            for (int i = 0; i < ingredientsArray.Length; i++)
            {
                // this is a string array, so check if the the string elements have something
                if (!string.IsNullOrEmpty(ingredientsArray[i]))
                    count++;
            }
            return count;
        }
        // toString method for the Listbox
        public override string ToString()
        {
            int chars = Math.Min(description.Length, 15);
            string descriptionTxt = description.Substring(0, chars);

            if (string.IsNullOrEmpty(descriptionTxt))
                descriptionTxt = "No Description";

            string textOut = String.Format("{0, -20}             {1, 4}                {2, -12}             {3, -15}",  
                Name, GetCurrentNoOfIngredients(), Category.ToString(), descriptionTxt);
            return textOut;
        }
    }
}
