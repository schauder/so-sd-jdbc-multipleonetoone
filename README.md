Example how to reference a single table from two (or more) entities.

The example domain consists of Cars and Trucks, which both reference an engine.
Engine instances are NOT SHARED between Cars and Trucks.
Instead they are considered part of either the Car or the Truck aggregate.

The prefered solution is to have two columns with car/truck id respectively in the Engine table.
This is demonstrated on the master branch.

Alternatively one can change the name of the back reference column to be the same for both references.
See f078ad74.

This has some problems though:

1. You can't have a foreign key on this column
2. calling `deleteAll` on either the truck or the car repository will delete *ALL* Engine entries!
    This is highly dangerous and therefor not recommended.

There is a related issue https://jira.spring.io/browse/DATAJDBC-128