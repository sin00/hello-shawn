package com.ericsson.li.groovy

 
beans {
	/*
		In class Car, we know that we defined variable gasoline as parameter of Car's constructor.
		Here we point out this variable's class(i.e. Gasoline), meanwhile set parameter's value of
		(Gasoline's) constructor
	*/	
    gasoline(Gasoline, 80)
 
	/*
		Define bean(i.e. car), and point out this bean's class(i.e. Car), meanwhile inject variable 
		gasoline to it. naturally we inject its variable(i.e. name) as well.  
	*/
    car1(Car, gasoline) {
        name = "Benz"
    }
    
    car2(Car, gasoline) {
        name = "BMW"
    }
}
