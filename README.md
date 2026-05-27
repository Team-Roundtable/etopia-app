# e-topia

## Structure
```
configuration/
    app.properties              // configuration
    deutsch.properties          // translation german
    english.properties          // translation english
src/
    main/                       // project
        java/                   // source
            Main/               // entrypoint
            ETopia/             // libGDX main handler
            views/              // minigames/map/overlays
                biogas/         // minigame
                geothermal/     // minigame
                grid/           // minigame
                information/    // transition view
                map/            // main map
                settings/       // language settings
                solar/          // minigame
                status/         // overlay health/time/energy
                wind/           // minigame
            time/               // time utilities
            shapes/             // shape utilities
            rendering/          // libGDX wrappers
            input/              // input handler and pi4j interface
            configuration/      // representation of app.properties
        plugins/                // plugin config
        resources/              // assets/fonts/etc.
    site/                       // maven site plugin
    test/                       // unit tests
```

## Documentation

For more details please referrer to this [repository](https://gitlab.fhnw.ch/ip12-25hs/ip12-hs25-team1/docu/-/blob/main/software(sad)/sad.adoc?ref_type=heads)

