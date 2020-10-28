# Kenerator

![Build](https://github.com/Rshalika/kenerator/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/15216-kenerator.svg)](https://plugins.jetbrains.com/plugin/15216-kenerator)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/15216-kenerator.svg)](https://plugins.jetbrains.com/plugin/15216-kenerator)

 
<!-- Plugin description -->

Intellij Idea And Android Studio plugin for generating compact equals method for Kotlin classes

## Usage

- Click alt+insert (Generate menu)
- Choose `Kenerator`
- Choose `Equals` or `Hashcode`

## Code format
    override fun equals(other: Any?): Boolean = other is BannerFace
            && super.equals(other)
            && name == other.name
            && image == other.image
            
    override fun hashCode(): Int = Objects.hash(
            super.hashCode(),
            name,
            image
    )

## Limitations 

 * Will not care for existing `equals` and `hashcode` methods. You have to delete them yourself.
 * Will not import used classes automatically.
 * You have to do the reformatting afterwards yourself.
 * Will not compare arrays with `Arrays.equals()` method.
 * Tested only on standard Kotlin classes. May have edge-cases.

<!-- Plugin description end -->

## Installation

- Using IDE built-in plugin system:
  
  <kbd>Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "Kenerator"</kbd> >
  <kbd>Install Plugin</kbd>
  
- Manually:

  Download the [latest release](https://github.com/Rshalika/kenerator/releases/latest) and install it manually using
  <kbd>Preferences</kbd> > <kbd>Plugins</kbd> > <kbd> ⚙️</kbd> > <kbd>Install plugin from disk...</kbd>



---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
