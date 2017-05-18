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
    class MainProgram
    {
        static void Main(string[] args)
        {
            // Declare and create an object of the Menu class
            Menu menu;  //deklaration - ref variable (null)
            menu = new Menu();  //objekt skapat


            // Call the start method of the Menu class to 
            // get started.
            menu.Start();
        }
    }
}
