using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Assignement4
{
    class Task
    {
        private DateTime date;
        private string description;
        private PriorityType priority;

        public Task ()
        {
            DefaultValues();
        }
        // overriding the Task constructor
        public Task (DateTime taskDate)
        {
            DefaultValues();
            this.date = taskDate;
        }
        // initializing the Task constructors
        public void DefaultValues()
        {
            date = DateTime.Now;
            description = "string.Empty";
            priority = PriorityType.Normal;
        }
        //The parameter value will automatically get the same type as the type of the Property(string in this example).
        // Property for the Description field
        public string Description
        {
            get { return description; }
            set
            {
                if (!string.IsNullOrEmpty(value))
                    description = value;
            }
        }
        // Proprerty for the priority enum
        public PriorityType Priority
        {
            get { return priority; }
            set
            {
                priority = value;
            }
        }
        // Property for this class' date
        public DateTime TaskDate
        {
            get { return date; }
            set
            { //no idea
                if (value != DateTime.MinValue)
                    date = value;
            }
        }
        // converting the priority property to string for the toString method
        private string GetPriorityString()
        {
            return Priority.ToString();
        }
        // I need to display the hour and minute and this method gets it from the Task date instance and converts it to string
        private string GetHourAndMinuteString()
        {
            return date.Hour.ToString() + ": " + date.Minute.ToString("00");
        }
        // printing the Task Class
        public override string ToString()
        {
            // {0, 12} - 12 defines the # of characters to be displayed
            string textOut = String.Format( "{0, -20}       {1, 10}        {2, 16}                  {3, -20}", 
                date.ToLongDateString(), GetHourAndMinuteString(), GetPriorityString(), Description);
            return textOut;
        }
    }
}
