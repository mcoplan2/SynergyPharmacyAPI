# Synergy Pharmaceutical

### Project Description:

Implements the functionality of a pharmacy where users can log in or register via API calls on a web page. After registering and authenticating, users will be able to see a list of medications available, a log of their refills, and request to purchase certain medications. Pharmacists will be able to maintain the medicine database, accept or reject requests, view logs of users who purchased a specific medicine, and view medication which is almost out of stock. This will be accomplished using the Spring Framework with a React frontend.

---

## User Stories

- [ ] Create a customer or pharmacist account.

- [ ] View available medication in the Pharmacy.

- [ ] Submit a request for refill or new prescription.

- [ ] Make a payment for your medication (if approved).

- [ ] View your current medications in the pharmacy.

- [ ] View a History/Log of your refills (transaction log).

- [ ] Pharmacists can add additional medication to the pharmacy

- [ ] Pharmacists can accept or reject requests.

- [ ] Pharmacists can view a history/log of each customer who purchased a specific medicine.

- [ ] Pharmacists can view a list of Medicines which are close to being out of stock (via some threshold).

---

### Stretch goals


- [ ] Add a way for customers to make a payment plan based on insurance.

- [ ] Users can include an insurance on their request so they are able to get a discount on their medicine.

- [ ] Add multiple pharmacies for different types of medications.

- [ ] Add a way for pharmacists to purchase additional medication ( may need to add an additional table).

- [ ] Can add a refill button that just adds x amount to stock when Pharmacists need to refill medications.


---

### User Model

id | name(firstname/lastname) | username | password | role

### Medicine Model

id | name | amountinstock | priceperunit | type | status

### Request Model

id | userId | medId | DosageCount | DosageFreq | Type | Status

### Payment Model

id | userId | medId | reqId | Amount | Status

---

### Enums


User Role - Customer, Pharmacist

Medicine Status - Instock, Out of Stock, Running Low

Medicine Type - Pill, Liquid, Drops, Inhalers

Request - Open, Approved, Denied

Payment - Unpaid, Paid in Full

---
