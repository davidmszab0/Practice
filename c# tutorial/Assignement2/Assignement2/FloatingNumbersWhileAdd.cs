using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

/*
Author: David M. Szabo
Date: 2016-10-09 
*/


namespace Assignement2
{
    class FloatingNumbersWhileAdd
    {
        //Declare a variable
        private double sum; //result of the summation

        public void Start()
        {
            // Call the method which writes the program info, title etc.
            WriteProgramInfo();
            ReadInputAndSumNumbers();
            ShowResults();
        }
        //Write a welcome text to the user
        private void WriteProgramInfo()
        {
            Console.WriteLine("------ Simulation of real numbers --------------");
            Console.WriteLine("\n                Using While Statement");
            Console.WriteLine("-------------------------------------------------");

            Console.WriteLine("Write 0 to finish!");
            Console.WriteLine("Make sure to use correct decimal character (,)");
            Console.WriteLine("-------------------------------------------------");
        }
        private void ReadInputAndSumNumbers()
        {
            //Read a number. If the value is given as 0, end the iteration
            //otherwise, accumulate the results in the instance variable sum
            bool done = false;

            while(!done)
            {
                // Read a number from the console (Input.ReadDoubleConsole can be called)
                double input = ReadInput();
                //Check if the number is zero
                if (Math.Round(input, 7) == 0.0)
                {
                //  If yes, set done true (end iteration)
                    done = true;
                } else
                //  If no, add the number to the sum 
                {
                    sum = sum + input;
                }
            }
        }
        private double ReadInput()
        {
            Console.Write("Write an amount to sum or zero to finish: ");
            double num = double.Parse(Console.ReadLine());
            return num;
        }
        //Show results to the user
        private void ShowResults()
        {
            //print results to the console window
            Console.WriteLine("-------------------------------------");

            //Note: 0 is the variable number, i.e. {0} will be substituted 
            // by the value stored in sum. \t is for tab
            Console.WriteLine("The sum is \t{0}", sum);
        }
    }
}
