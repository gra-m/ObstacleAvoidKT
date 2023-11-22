# Commentary

### Start:
* Not auspicious, cut rather than copied utils over from sampler. Then saw that extension function (pointless) toInternalFile()
was not being found by IntelliJ, *for no good reason*. I assumed it was because I'd updated gradle and IntelliJ had had a brain fart.
The fact that the above function was not importing for some unknown reason meant that I did not see my mistake in the Sampler project,
all it thats around an hour of screwing around pointlessly. If I find the gremlin responsible, it is dead.

### Continued weirdness:
22/11/2023 Admittedly not feeling great today, but this project is going so slowly in comparison to previous. OK, new language,
but. Experienced some strange inconsistencies today with how grid was/was not rendered. 
There was nothing wrong with grid rendering code but it never rendered.. until it suddenly did. Back-tracking failed to remove it
again..
```kotlin
//using this:
        config.setWindowedMode(GameConfig.WIDTH, GameConfig.HEIGHT)

// in place of expected lgdx2 code or setWindowSizeLimits
```
