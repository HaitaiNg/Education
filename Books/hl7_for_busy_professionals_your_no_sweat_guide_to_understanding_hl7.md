# HL7 For Busy Professionals: Your No Sweat Guide to Understanding HL7

In the past, railroads were not standardized. Each railroad company had a different gauge (the horizontal distance between two rails). There were 20 variations.

Due to this, there were multiple railroad lines, and there was a lot of unloading and loading.

Over time, the railroad system eventually standardized (e.g., track gauge, signal system, time zone, and all carts had a fixed coach height).

---

## Healthcare Standardization

Healthcare is not standardized. This makes integrations and sharing information outside of the organization very difficult.

Some IT vendors came up with a solution to try and replace the standalone systems. This product is called the EHR (electronic health record) company (e.g., Cerner, Epic, Meditech, AllScripts, Athena).

HL7 is an ANSI OSI layer 7 application protocol. It tries to define the rules for exchanging data.

### MAC Address:
- **MAC address**: Media Access Control ID. A six-byte number stamped on every network interface card. This is referred to as the hardware or physical address. It cannot be changed.

For local addresses, the MAC address is all you need to determine where to send the packets (e.g., a post office delivery man within the same neighborhood).

There are multiple ways for data to become inconsistent. HL7 & Healthcare is late to the game of business process automation. Banks and other financial institutions use SWIFT.

---

## Communication Models

### Hub-and-Spoke Model
The hub-and-spoke model solved the problems of exponentially increasing connections of computer systems.

### Publish-and-Subscribe Model
A channel connects to the hub and subscribes to a topic; if there is a modification to one of the sources, the source sends a notification to the hub; the subscribers pull from these topics.  
*(Pro Tip: The major pro of this technology is that sources do not need to keep track of their recipients. The hub keeps track of who owes what.)*

---

## Three Standards for Integration:
1. **Message transfer standard**: How a message will be sent.
2. **Message format standard**: What a message will look like.
3. **Data standard**: How healthcare information will be organized in a message.

HL7 cares about: message format; data standards.

---

## HL7 Versions

HL7 is backwards compatible, and because of this, there are a lot of optional fields in their spec.  
“Every time two systems need to communicate, a unique HL7 interface has to be created with all options and customization involved” (40).

HL7 has two versions: 
- **v2.5xx** (old)
- **v3.0** (very old, Europe uses it)

---

## Message Types
### Soliciting vs Unsoliciting Messages:
- **Solicit Messaging**: “To ask for something from someone.”
- **Unsolicited Messaging**: “To get something from someone without asking.”

If a system asks another system for a message, that is soliciting (e.g., database query).  
Most HL7 messages are unsolicited. This means we only send updates when ready; we do not bother or pester the person we are asking.

---

## Common HL7 Message Types
- **ADT (Admit, Discharge, Transfer)** messages: All Patient Administration Messages are ADT.
- **Patient Administration Messages**: These messages should contain everything related or associated with the patient.

---

## Message Workflow

HL7 triggers an event. An HL7 message is created only when something happens. The workflow looks like this:
1. Hospital staff completes the ADMIT form and hits “Enter”.
2. An HL7 message is created.

---

## Message Acknowledgement
A message is sent from the recipient to the sender to notify that the message was received:
- **AA**: Positive acknowledgement (Happy path)
- **AE**: Negative acknowledgement (Message contains error)
- **AR**: Negative (processing error)

---

## Types of Acknowledgements
### Original vs Enhanced HL7:
- **HL7 Original**: Basic, straightforward notifications primarily using ADT messages.
- **HL7 Enhanced**: Advanced notifications supporting a wider range of message types with sophisticated routing, filtering, transformation, and security features.

---

## The Building Blocks of HL7

- A message is made up of **segments**.
- A segment is made up of **fields** separated by pipes (`|`).
- A field is made up of **components** separated by a caret (`^`).
- A component is made up of **sub-components** separated by an ampersand (`&`).

To read a message, you will need the **interface specification document** (interface spec).

---

## High-Level Anatomy of a Message
- **HEAD**: The first two or three segments at the top of the message (control segments).
- **BODY**: The rest (data segments).

For additional information: [HL7.org -> Standards -> Primary S
