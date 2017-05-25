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
        /*
            <summary>
            This class takes care of the whole process of
            (1) reading input from the console window
            (2) performing the calculation and
            (3) printing the results to a console window
            Let objects take care of everything that belongs to the object!
            </summary>        
        */
    class WholeNumbersForAdd
    {
        // Declare a variable 
        private int numOfInput; //num of values to be added
        private int sum; // result of the summation

        //public void method that performs the whole process
        public void Start()
        {
            // Call the method which writes the program info, title etc. 
            WriteProgramInfo();
            ReadInput();
            SumNumbers();
            ShowResults();
            
        }
        // void method that reads user input
        private void ReadInput()
        {
            // Determine how many numbers are to be added
            Console.WriteLine("Number of values to sum?");
            numOfInput = int.Parse(Console.ReadLine());
            Console.WriteLine(); // blank line
        }
        private void WriteProgramInfo()
        {
            //you can use \n to put a blank line
            Console.WriteLine("\n\n +++++++ Summation of whole numbers ++++++++");
            Console.WriteLine("                  Using a for statement\n");
            Console.WriteLine(); // blank line
        }
        // void method that sums up the numbers as read and the results are stored
        // in an instance variable sum
        private void SumNumbers()
        {
            //Local variables
            int index; //counter variable
            int num = 0; // stores the value the user gives

            //A for statement that iterates
            for (index = 0; index < numOfInput; index++)
            {
                Console.WriteLine("Please give the value number 1 (whole number): ");
                num = int.Parse(Console.ReadLine());
                // add the user input to the sum each time the user inputs something
                sum = sum + num;
    }
        } //Sumnumbers

        private void ShowResults()
        {
            //print results to the console window
            Console.WriteLine("-------------------------------------");

            //Note: 0 is the variable number, i.e. {0} will be substituted 
            // by the value stored in sum. \t is for tab
            Console.WriteLine("The sum is \t{0}", sum);
        }
    } //class
} //namespace
