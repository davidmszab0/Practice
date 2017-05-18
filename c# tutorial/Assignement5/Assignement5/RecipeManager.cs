using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

/*
    RecipeManager defines an array of recipes.
    Saves a number of recipes in another object (write the class RecipeManager).
*/
namespace Assignement5
{
    class RecipeManager
    {
        private Recipe[] recipeList;
        public RecipeManager(int maxRecipes)
        {
            recipeList = new Recipe[maxRecipes];
        }
        // ---------------- Properties ------------------
        // same as in the recipe class
        public Recipe GetRecipeAt(int index)
        {
            if (CheckIndex(index))
                return recipeList[index];
            else
                return null;
        }
        public int MaxNoOfRecipes
        {
            get { return recipeList.Length; }
        }
        // ---------------- Methods ------------------
        // find the vacant position in the array
        private int FindVacantPosition()
        {
            for (int i = 0; i < recipeList.Length; i++)
            {
                // OBS the array is of objects, not Strings 
                if (recipeList[i] == null)
                    return i;
            }
            return -1;
        }
        // check if the index is within the correct range
        public bool CheckIndex(int index)
        {
            if (0 <= index && index < recipeList.Length)
                return true;

            return false;
        }
        // add a new recipe to the vacant positin in the recipeList array
        public bool Add(Recipe newRecipe)
        {
            bool ok = false;

            int index = FindVacantPosition();
            if (0 <= index)
            {
                recipeList[index] = newRecipe;
                ok = true;
            }
            return ok;
        }
        // same as the method before
        public bool Add(string name, FoodCategory categ, string[] ingredients, string desc)
        {
            bool ok = false;
            int index = FindVacantPosition();
            if (0 <= index)
            {
                Recipe recipe = new Recipe(ingredients.Length);
                recipe.Name = name;
                recipe.Ingredients = ingredients;
                recipe.Category = categ;
                recipe.Description = desc;

                recipeList[index] = recipe;
                ok = true;
            }
            return ok;
        }
        // get number of recipes
        public int CurrentNumOfRecipes()
        {
            int count = 0;
            for (int i = 0; i < recipeList.Length; i++)
            {
                if (recipeList[i] != null)
                    count++;
            }
            return count;
        }
         // returns a string[] out of the RecipeList object array
        public string[] RecipeListToString()
        {
            string[] stringArray = Array.ConvertAll<Recipe, string>(recipeList, ConvertObjectToString);
          
            return stringArray;
        }
        // helper method
        private string ConvertObjectToString(Recipe obj)
        {
            return (obj == null) ? string.Empty : obj.ToString();
        }
    }
}
