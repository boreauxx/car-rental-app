# Vehicle Rental Application

Simple yet functional application for calculating the rental and insurance 
cost you've paid whilst renting a vehicle.
To use the application simply run the main class and follow the instructions on the
CLI.

1. As the application starts You will be prompted to enter a name (optional)

2. You will be asked question about the vehicle (brand, model, value) from which
brand and model are optional but **value** is crucial for the business logic.

3. Depending on the vehicle you've chosen to rent you'll be asked a question
 - **car** - what is the car's safety rating, 1 to 5, 1 being the lowest, car's with safety > 3 receive preferable insurance
 - **motorcycle** - how old are you, being under 25 is going to increase your insurance
 - **cargo van** - what is your driver experience, in years (3years, etc.), if you have more than 5 years you will receive preferable insurance

4. Information about the renting period 
 - **start date** - when did you rent the vehicle
 - **end date** - until when did you rent the vehicle
 - **return date** - when did you return the vehicle

5. Keep in mind the following things:
 - the date format is dd/MM/yyyy (03/06/2024)
 - the end and return date **cannot** be before the start date
 - if you've returned the vehicle early you'll received less rental cost per day for the remaining days and no insurance

6. After you've completed filling the information you'll receive and **Invoice** having all the information you need formatted in a readable and fashionable way.

## Objects Folder
the `objects` folder contains:
 - the `vehicle` class, abstract, used for every vehicle instance
 - to add a new vehicle simply created it, add deisred new fields and extends Vehicle
 - the **invoice** class is used for the output via its `format()` method
 - the **rentAndInsurance** class is used for easier storing of the values

## Services Folder 
the `services` folder contains:
 - all the business logic
 - interaface and its implementation similar to a service bean in a RESTful API
 - `processing service` with methods for creating a vehicle, calucating its rent and insurance, setting values based on input, checking for discounts, formatting
 - additional `input service` to handle all user inputs and verify them separetly to keep the main file clean

## Constants Folder
the `constants` folder contains:
 - all messages, errors and static values for objects
 - inside you can see the numbers used for the business logic 
 - insurace based on vehicle, surcharge or discount based on vehicle specific factor, etc..