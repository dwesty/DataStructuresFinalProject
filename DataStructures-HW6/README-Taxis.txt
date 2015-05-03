For Taxis, we had three main data structures:

A Graph class, common to all three tasks.

An adaptable min heap structure, holding the serial numbers of the taxis with keys being distance from source

Two HashMap structures, mapping from serial number to location and location to serial number. (This leads to a one-to-one relationship between serial and location which isn’t there. This would need to be fixed.)
———————

We utilized Dijsktra’s shortest path algorithm, using a source integer as given by the user. However, in addition to updating the prev[] array, it also updates the heap via our location->serial HashMap. At the end of shortest path method, we return prev[] so we can trace it later.

We removeMin() from our heap 3 times. (In the suggested output it never asked that user for what k was, but alway showed 3 drivers able to respond. So we assumed k = 3.) Then display and ask our user which driver they want.

Then, using our serial->location HashMap, we are able to recursively trace though prev[] in printRoute() to find our suggested route. This method yields slightly output than suggested, but the edges and order remain the same. This method just prints out the directions such that the vertex you end at on one direction is where you start at in the next. That is:

	(Peabody Institute, Baltimore Penn Station)
	(Baltimore Penn Station, N Charles St & Art Museum Dr)
	(N Charles St & E 33rd St, N Charles St & Art Museum Dr)

instead of 

	(Baltimore Penn Station, Peabody Institute)
	(Baltimore Penn Station, N Charles St & Art Museum Dr)
	(N Charles St & E 33rd St, N Charles St & Art Museum Dr)

We get the time from our min heap.