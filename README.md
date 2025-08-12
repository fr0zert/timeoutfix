# TimeoutFix

**A simple Minecraft Fabric mod for 1.21.7** that allows you to overwrite the default server-side login timeout time, so players with slow internet connections (or heavy modpacks) don’t get disconnected before they finish downloading resources.

## Usage

1. Drop the mod `.jar` into your server’s `mods` folder.
2. Start and stop the server. (so default config file is created) / or create manually before restart
3. Edit the config file (`config/TimeOutFixConfig.properties`) to set your preferred timeout in **server ticks**.
```toml
# Config uses tisks, so if u want specify second just do (seconds * 20) // default value is 600 
login_time_out=1800
```
4. Start / Restart the server.

## Contributions

I accept **pull requests** and **code improvements**.
If you know Java better than me (which is likely), feel free to clean up or optimize the code / or optimize build system, this stuff is sucks in java idk how to use it.

## Disclaimer

* Tested only on **Fabric 1.21.7** server.

## Why I made this

I made this in about **1–2 hours**, despite never having written Java before in my life.
So yes the code might be badly written.
But hey, it works.

This was created because I have a friend with terrible internet.
Our server has a lot of mods (like [Emotecraft](https://modrinth.com/mod/emotecraft) with server-side emotes), which means joining can take a while. Without this mod, players with slow downloads would get kicked for "Timed Out" before they even joined. This fixes that by letting the server wait longer.
