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
    class Menu
    {
        /// <summary>
        /// Shows a menu and reads the user's choice of how to show the 
        /// current time, date or both
        /// </summary>
        public void Start()
        {
            int choice = -1;

            while (choice != 0)  //while done = false (done = false at start, from above)
            {
               
                // Read the user's choice
                //choice = int.Parse(Console.ReadLine());
                choice = ReadMenuChoice();
                
                //Determin which message to show
                //Depending on the value of the choice, create an instance of this class displayed on the menu
                switch (choice)
                {
                    case 0:    //User selects to exit the program
                        choice = 0;
                        break;

                    case 1: //Menu item 1 (the for statement)

                        // Declare a local reference variable and create an instance of WholeNumberIsFor
                        WholeNumbersForAdd sumObj = new WholeNumbersForAdd();
                        // Call the obj start method
                        sumObj.Start();
                        break;

                    case 2:
                        // Declare a local reference variable and create an instance of WholeNumberIsFor
                        FloatingNumbersWhileAdd floatObj = new FloatingNumbersWhileAdd();
                        // Call the obj start method
                        floatObj.Start();
                        break;

                    case 3:
                        CurrencyConverter currencyObj = new CurrencyConverter();
                        currencyObj.Start();
                        break;

                    case 4:
                        WorkingSchedule workingObj = new WorkingSchedule();
                        workingObj.Start();
                        break;

                    default:
                        Console.WriteLine("Choose one from the list: ");
                        break;
                } //switch
            }//while
        }//Start

        /// <summary>
        /// Read the user's menu choice
        /// </summary>
        /// <returns>A value representing the user's choice.</returns>
        private int ReadMenuChoice()
        {
            bool done = false;
            int iValue = -1;

            do
            {
                WriteMenuText(); // show the menu
                if (int.TryParse(Console.ReadLine(), out iValue))
                {
                    if ((iValue >= 0) && (iValue <= 4))
                        done = true;
                }

                if (!done)
                    Console.WriteLine("Invalid choice, try again!\n");

            } while (!done);

            return iValue;
        }//ReadMenuChoice

        /// <summary>
        /// Help method showing the menu text
        /// </summary>
        private void WriteMenuText()
        {
            WriteBlankLines(3);
            Console.WriteLine("-------------------------------------------------");
            Console.WriteLine("\n                MAIN MENU");
            Console.WriteLine("-------------------------------------------------");
            Console.WriteLine(" Whole Number with For                   : 1");
            Console.WriteLine(" Floating Point Numbers with While       : 2");
            Console.WriteLine(" Currency converter with do-while loop   : 3");
            Console.WriteLine(" Work schedule                           : 4");
            Console.WriteLine(" Exit the program                        : 0\n");
            Console.WriteLine("-------------------------------------------------");
            WriteBlankLines(2);

            Console.Write(" Your Choice: ");
        }
        /// <summary>
        /// Method that prints a number of blanklines to the console
        /// window.  The number of lines is determined by the
        /// the caller of the method
        /// </summary>
        /// <param name="count">Number of lines to print.</param>
        private void WriteBlankLines(int count)
        {
            for (int i = 0; i < count; i++)
                Console.WriteLine();
        }

    }
}
