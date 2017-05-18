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
    class WorkingSchedule
    {
        public void Start()
        {
            ShowResults();
        }
        // print out the program info to present the user with options
        private void WriteProgramInfo()
        {
            Console.WriteLine("Select the schedule type from the menu: ");
            Console.WriteLine("-----------------------------------------");
            Console.WriteLine("1 Show a list of the weekends to work");
            Console.WriteLine("2 Show a list of the nights to work");
            Console.WriteLine("0 Return to the main menu");
        }
        // This helper method reads the user input
        private int ReadInput()
        {
            Console.Write("Your Choice: ");
            int inputNumber = int.Parse(Console.ReadLine());
            return inputNumber;
        }
        // This method executes the calculation and shows the results
        private void ShowResults()
        {
            bool done = false;

            // while loop is used because we are continously waiting for the user choose a menu option
            // alternatively a do-while loop can be used to print the menu option first, and executing that part
            // not depending from the boolean condition
            while (!done)
            {
                // we print the menu options
                WriteProgramInfo();
                // we ask for a user input and store it in a variable
                int userInput = ReadInput();
                Console.WriteLine("Your schedule of the above option is as follows: ");

                // depending from the user input, we either print the weeks or exit to the main menu (0 input)
                if (userInput == 1)
                {
                    // this method prints out the weeks depending from userinput, if it was 1 or 2
                    printWeeks(userInput);

                } else if (userInput == 2)
                {
                    printWeeks(userInput);

                } else if (userInput == 0)
                {
                    done = true;
                } else
                {
                    // printing blank lines
                    Console.WriteLine();
                    Console.WriteLine();
                    Console.WriteLine();
                    Console.WriteLine("Please enter a valid number!");
                    Console.WriteLine();
                    Console.WriteLine();
                    Console.WriteLine();
                }

                Console.WriteLine("-----------------------------------------");
            }
        }
        // print the week numbers depending from the user input
        // I am using a for loop, because the number of weeks is known (52) and 
        // also the week interval is known
        private void printWeeks(int userInput)
        {
            if (userInput == 1)
            {
                // i+=3 iterates the for loop by 3 not 1 as i++ does
                for (int i = 1; i < 53; i += 3)
                {
                    Console.WriteLine("Week: " + i);
                }
            }
            else if (userInput == 2)
            {
                for (int i = 6; i < 53; i += 5)
                {
                    Console.WriteLine("Week: " + i);
                }
            } 
        }
    }
}
