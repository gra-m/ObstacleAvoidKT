# Commentary

### Start:
* Not auspicious, cut rather than copied utils over from sampler. Then saw that extension function (pointless) toInternalFile()
was not being found by IntelliJ, *for no good reason*. I assumed it was because I'd updated gradle and IntelliJ had had a brain fart.
The fact that the above function was not importing for some unknown reason meant that I did not see my mistake in the Sampler project,
all it thats around an hour of screwing around pointlessly. If I find the gremlin responsible, it is dead.