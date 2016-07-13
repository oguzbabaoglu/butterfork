# DEPRECATED

ButterKnife supports library projects starting with version [8.2.0](https://github.com/JakeWharton/butterknife/blob/master/CHANGELOG.md#version-820-2016-07-10). No new development will be taking place on this fork.

Butter Fork
===========

![Logo](website/static/logo.png)

A fork of [ButterKnife](https://github.com/JakeWharton/butterknife) with support for Library projects (Solves the non-final id problem).

This change is incompatible with the current library and is therefore distributed under a different name. See the [Limitations](#limitations) and [Why Fork?](#why-fork) sections below.

Usage is identical apart from using 'B' instead of 'R'. The solution does not depend on reflection.

```java
@BindView(B.id.user) EditText username;
@BindView(B.id.pass) EditText password;
//..
ButterKnife.bind(this);
```

Download
----------

```groovy

buildscript {
  repositories {
    jcenter()
  }
  dependencies {
    classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
    classpath 'com.oguzbabaoglu:butterfork-plugin:2.0.0'
  }
}

// It is important that the library apt plugin are applied before butterfork-plugin
apply plugin: 'com.android.library'
apply plugin: 'com.neenbedankt.android-apt'
apply plugin: 'com.oguzbabaoglu.butterfork-plugin'

// No need to declare dependencies, this is handled by the plugin

```

How does it work?
-----------------

ButterKnife does not work with library projects because R fields are not final, and Java annotations **must** have final values.

ButterFork adds a `generateB` task right after `processResources` (task that generates R). `generateB` builds a B class containing all field names in the R class as final String values.
```java
public final class B {
  public static final class id {
    public static final String text_view = "example.R.id.text_view";
  }
  public static final class string {
    public static final String app_name = "example.R.string.app_name";
  }
}
```

Final String values from the generated B class are used in place of R values in the annotations.
```java
@BindView(B.id.text_view) TextView textView;
```

The processor reads these annotations and generates binder classes. ButterKnife uses the resolved int value from the R class, but ButterFork instead places a reference back to the R class inside the generated code.

**ButterKnife:** `view = finder.findRequiredView(source, 2131755092, "field 'textView'")`

**ButterFork:** `view = finder.findRequiredView(source, example.R.id.textView, "field 'textView'")`

Limitations
-----------
- Currently B is only generated for the 'local' R. To use R references from other libraries you need to use String literals like `@BindView("android.R.id.button1")`.

- B is only generated when gradle runs, whereas R is continually updated by the IDE. Rebuild the project to update it.

- Using String values means we lose the type-safety of the original library, the IDE will not warn you of typos or wrong references.

Why Fork?
---------------

There are a couple of issue threads where this problem was discussed for ButterKnife. In these you can find detailed explanations from Jake Wharton about why it is a bad idea to add this support to the existing library:
- [Issue #2](https://github.com/JakeWharton/butterknife/issues/2) - Add Support For Non-Final IDs
- [Issue #45](https://github.com/JakeWharton/butterknife/issues/45) - Library project issue
- [Issue #100](https://github.com/JakeWharton/butterknife/issues/100) - Injection does not work in library projects
- [Issue #123](https://github.com/JakeWharton/butterknife/issues/123) - take field/method name as default id

License
-------

    Original work Copyright 2013 Jake Wharton
    Modified work Copyright 2015 Oguz Babaoglu

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
