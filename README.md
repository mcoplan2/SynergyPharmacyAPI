# synergy_p2_api

# Synergy Pharmaceutical

# User Stories

Create a Customer or Pharmacist Account

View available medicines in the Pharmacy

Register your Medicine on the website

Submit an application for refill or new prescription.

Make a payment for your medicine (if accepted)

View your current Medicine and renewal/refill date

View a History/Log of your refills

Pharmacists can add additional medicines to the Pharmacy

Pharmacists can accept or reject applications
Pharmacists can view a history/log of each customer who purchased a specific medicine
Pharmacists can view a list of Medicines which are close to being out of stock (via some threshold)

User Model
id | name(firstname/lastname) | username | password | role
Medicine Model
id | name | amountinstock | priceperunit | type | status
Request Model
id | userId | medId | DosageCount | DosageFreq | Type | Status
Payment Model
id | userId | medId | reqId | Amount | Status

Enums
User Role - Customer, Pharmacist
Medicine Status - Instock, Out of Stock, Running Low
Medicine Type - Pill, Liquid, Drops, Inhalers
Request - Open, Accepted, Denied
Payment - Unpaid, Paid in Full

Stretch goals
Add a way for customers to make a payment plan based on insurance
Users can include an insurance on their request so they are able to get a discount on their medicine
Add multiple pharmacies for different types of medications
Add a way for pharmacists to purchase additional medication ( may need to add an additional table).
Can add a refill button that just adds x amount to stock when Pharmacists need to refill medications.
