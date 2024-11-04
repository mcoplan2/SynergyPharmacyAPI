# Synergy Pharmaceutical

**Project Overview**

Synergy Pharmaceutical is a Spring Boot-based backend for a full-featured pharmacy application. The application allows users to register, authenticate, and manage their medication and refill requests. Pharmacists can manage medication inventory, approve or deny customer requests, and monitor stock levels. The frontend is built with React, providing an interactive and intuitive user experience.

Website Demo here: https://synergy-ui-next-js-ten.vercel.app/

## Features

### User Stories
- **Account Management**: Create either a customer or pharmacist account.
- **Medication Viewing**: Customers can view available medications in the pharmacy.
- **Request Submission**: Users can request a refill or new prescription.
- **Payments**: Customers can make payments for approved medications.
- **Medication History**: Users can view a history of their refills (transaction log).
- **Pharmacy Management**:
  - Pharmacists can add medications to the pharmacy inventory.
  - Pharmacists can accept or reject medication requests.
  - Pharmacists can view transaction logs for each customer.
  - Pharmacists can monitor stock levels and be alerted when medications are low.

### TODO List
- **Insurance-Based Discounts**: Users can apply insurance discounts to medication requests.
- **Stock Replenishment**: Add functionality for pharmacists to order additional medication and update stock.
- **Support Ticket**: Add functionality to create support tickets.

## Data Models

### User Model
| Field     | Description           |
|-----------|-----------------------|
| id        | Unique identifier     |
| name      | Full name (first/last)|
| username  | Unique username       |
| password  | Encrypted password    |
| role      | User role (Customer/Pharmacist)|

### Medication Model
| Field           | Description                    |
|-----------------|--------------------------------|
| id              | Unique identifier              |
| name            | Medication name                |
| amountInStock   | Quantity available             |
| pricePerUnit    | Price per unit                 |
| type            | Medication type (e.g., Pill)   |
| status          | Inventory status               |

### Request Model
| Field          | Description                  |
|----------------|------------------------------|
| id             | Unique identifier            |
| userId         | ID of the requesting user    |
| medId          | ID of the medication         |
| dosageCount    | Prescribed dosage count      |
| dosageFreq     | Dosage frequency             |
| type           | Request type                 |
| status         | Request status (e.g., Approved)|

### Payment Model
| Field     | Description                       |
|-----------|-----------------------------------|
| id        | Unique identifier                 |
| userId    | ID of the user making the payment |
| medId     | ID of the medication              |
| reqId     | Related request ID                |
| amount    | Payment amount                    |
| status    | Payment status (e.g., Paid)       |

## Enums

- **User Role**: `Customer`, `Pharmacist`
- **Medication Status**: `In Stock`, `Out of Stock`, `Running Low`
- **Medication Type**: `Pill`, `Liquid`, `Drops`, `Inhaler`
- **Request Status**: `Open`, `Approved`, `Denied`
- **Payment Status**: `Unpaid`, `Paid in Full`
