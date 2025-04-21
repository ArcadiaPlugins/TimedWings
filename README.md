![Java](https://img.shields.io/badge/language-Java-blue.svg)
![Platform](https://img.shields.io/badge/platform-Spigot%20%7C%20Paper-yellow)
![License](https://img.shields.io/github/license/ArcadiaPlugins/TimedWings)
![Version](https://img.shields.io/github/v/tag/ArcadiaPlugins/TimedWings?label=latest)

[![Buy Me a Coffee](https://img.shields.io/badge/Support-Buy%20Me%20a%20Coffee-ffdd00?style=for-the-badge&logo=buymeacoffee)](https://buymeacoffee.com/arcadiaplugins)

# 🕊️ TimedWings

**TimedWings** is a developer-friendly, modular timed flight plugin for Minecraft servers.

It supports popular plugins like FabledSkyBlock, WorldGuard, and BentoBox with automatic integration, and provides a clean API for custom extensions.

---

📘 **Full documentation available at**: [plugins.arcadia.tc](https://plugins.arcadia.tc)

---

## 📦 Installation

Download the plugin from one of the following sources:

- [GitHub Releases](https://github.com/ArcadiaPlugins/TimedWings/releases/latest)
- [SpigotMC](https://www.spigotmc.org/resources/00000) *(coming soon)*
- [Polymart](https://polymart.org/resource/00000) *(coming soon)*

Place the `.jar` file in your `/plugins` folder and start the server to generate configuration files.

For detailed setup and configuration, see the [Wiki](https://plugins.arcadia.tc/en/timedwings/installation).

---

## 📚 Documentation

Looking for placeholders, commands, configuration, or developer API?

👉 Full documentation available on the [Wiki](https://plugins.arcadia.tc/en/timedwings)

---

## 🧑‍💻 Developer Usage

```java
PlayerData data = TimedWings.getInstance()
    .getPlayerDataManager()
    .getPlayerData(player);

data.addFlightTime(600); // Adds 10 minutes of flight time
```

More: [Developer API](https://plugins.arcadia.tc/en/timedwings/developer_api)

---

## 🔗 Add as Dependency (JitPack)

TimedWings can be added to your project via [JitPack](https://jitpack.io/#ArcadiaPlugins/TimedWings)

### Maven

```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>

<dependency>
  <groupId>com.github.ArcadiaPlugins</groupId>
  <artifactId>TimedWings</artifactId>
  <version>latest</version> <!-- Replace latest tag with latest released TimedWings version -->
</dependency>
```

### Gradle

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.ArcadiaPlugins:TimedWings:latest' // Replace with latest tag
}
```

> 🧪 Tip: You can replace `latest` with a specific tag or commit hash

---

## 🤝 Contributing

Contributions, feature requests, and integrations are welcome. Feel free to open an issue or PR!

---
## ☕ Support Arcadia Plugins

If you like what we do, consider supporting us on [Buy Me a Coffee](https://buymeacoffee.com/arcadiaplugins) 💙  
Your support helps us continue building awesome plugins like TimedWings.

---

Made with ❤️ by [ArcadiaPlugins](https://github.com/ArcadiaPlugins)

<!-- 
🧠 Note: This project was inspired by some structural patterns seen in FabledSkyBlock.
No original code was used or copied. All logic and implementations are written from scratch.
-->
