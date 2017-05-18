namespace Assignement5
{
    partial class FormIngredient
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.label1 = new System.Windows.Forms.Label();
            this.lblNoIngredients = new System.Windows.Forms.Label();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.btnIngredientCancel = new System.Windows.Forms.Button();
            this.btnIngredientOK = new System.Windows.Forms.Button();
            this.button3 = new System.Windows.Forms.Button();
            this.button2 = new System.Windows.Forms.Button();
            this.lstIngredients = new System.Windows.Forms.ListBox();
            this.btnAddIngredient = new System.Windows.Forms.Button();
            this.txtIngredient = new System.Windows.Forms.TextBox();
            this.groupBox1.SuspendLayout();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(21, 38);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(111, 13);
            this.label1.TabIndex = 0;
            this.label1.Text = "Number of Ingredients";
            // 
            // lblNoIngredients
            // 
            this.lblNoIngredients.AutoSize = true;
            this.lblNoIngredients.Location = new System.Drawing.Point(223, 38);
            this.lblNoIngredients.Name = "lblNoIngredients";
            this.lblNoIngredients.Size = new System.Drawing.Size(14, 13);
            this.lblNoIngredients.TabIndex = 1;
            this.lblNoIngredients.Text = "#";
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.btnIngredientCancel);
            this.groupBox1.Controls.Add(this.btnIngredientOK);
            this.groupBox1.Controls.Add(this.button3);
            this.groupBox1.Controls.Add(this.button2);
            this.groupBox1.Controls.Add(this.lstIngredients);
            this.groupBox1.Controls.Add(this.btnAddIngredient);
            this.groupBox1.Controls.Add(this.txtIngredient);
            this.groupBox1.Location = new System.Drawing.Point(24, 76);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(360, 280);
            this.groupBox1.TabIndex = 2;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Ingredient";
            // 
            // btnIngredientCancel
            // 
            this.btnIngredientCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
            this.btnIngredientCancel.Location = new System.Drawing.Point(157, 251);
            this.btnIngredientCancel.Name = "btnIngredientCancel";
            this.btnIngredientCancel.Size = new System.Drawing.Size(75, 23);
            this.btnIngredientCancel.TabIndex = 6;
            this.btnIngredientCancel.Text = "Cancel";
            this.btnIngredientCancel.UseVisualStyleBackColor = true;
            // 
            // btnIngredientOK
            // 
            this.btnIngredientOK.DialogResult = System.Windows.Forms.DialogResult.OK;
            this.btnIngredientOK.Location = new System.Drawing.Point(54, 251);
            this.btnIngredientOK.Name = "btnIngredientOK";
            this.btnIngredientOK.RightToLeft = System.Windows.Forms.RightToLeft.No;
            this.btnIngredientOK.Size = new System.Drawing.Size(75, 23);
            this.btnIngredientOK.TabIndex = 5;
            this.btnIngredientOK.Text = "Ok";
            this.btnIngredientOK.UseVisualStyleBackColor = true;
            // 
            // button3
            // 
            this.button3.Location = new System.Drawing.Point(280, 138);
            this.button3.Name = "button3";
            this.button3.Size = new System.Drawing.Size(61, 23);
            this.button3.TabIndex = 4;
            this.button3.Text = "Delete";
            this.button3.UseVisualStyleBackColor = true;
            // 
            // button2
            // 
            this.button2.Location = new System.Drawing.Point(280, 109);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(61, 23);
            this.button2.TabIndex = 3;
            this.button2.Text = "Edit";
            this.button2.UseVisualStyleBackColor = true;
            // 
            // lstIngredients
            // 
            this.lstIngredients.FormattingEnabled = true;
            this.lstIngredients.Location = new System.Drawing.Point(17, 54);
            this.lstIngredients.Name = "lstIngredients";
            this.lstIngredients.Size = new System.Drawing.Size(257, 186);
            this.lstIngredients.TabIndex = 2;
            // 
            // btnAddIngredient
            // 
            this.btnAddIngredient.Location = new System.Drawing.Point(280, 28);
            this.btnAddIngredient.Name = "btnAddIngredient";
            this.btnAddIngredient.Size = new System.Drawing.Size(61, 23);
            this.btnAddIngredient.TabIndex = 1;
            this.btnAddIngredient.Text = "Add";
            this.btnAddIngredient.UseVisualStyleBackColor = true;
            this.btnAddIngredient.Click += new System.EventHandler(this.btnAddIngredient_Click_1);
            // 
            // txtIngredient
            // 
            this.txtIngredient.Location = new System.Drawing.Point(17, 28);
            this.txtIngredient.Name = "txtIngredient";
            this.txtIngredient.Size = new System.Drawing.Size(257, 20);
            this.txtIngredient.TabIndex = 0;
            // 
            // FormIngredient
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(409, 368);
            this.Controls.Add(this.groupBox1);
            this.Controls.Add(this.lblNoIngredients);
            this.Controls.Add(this.label1);
            this.Name = "FormIngredient";
            this.Text = "FormIngredient";
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label lblNoIngredients;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.Button button3;
        private System.Windows.Forms.Button button2;
        private System.Windows.Forms.ListBox lstIngredients;
        private System.Windows.Forms.Button btnAddIngredient;
        private System.Windows.Forms.TextBox txtIngredient;
        private System.Windows.Forms.Button btnIngredientCancel;
        private System.Windows.Forms.Button btnIngredientOK;
    }
}