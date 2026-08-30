# jank commons
This repo contains a set of curated packages which maintained by the jank
community.

## Criteria for new packages
We are interested in packages which are:

1. Popular libraries (OpenGL, SDL, Boost, etc)
2. Highly desired by the community
3. Well-written, following jank's packaging idioms

To submit a package for consideration, please make an issue to first discuss the
package. After the proposal is approved, you may submit a pull request with the
new package.

## How to write a new package
We're happy you're interested in adding a package to the jank commons! The
general process for creating a package looks like this.

1. Determine if you need to build the package from source or if you can rely on
   `pkg-config`. Generally, if popular Linux distros and macOS package managers
   contain stable versions of your package, you can get away with using
   `pkg-config` instead of building from source. There's a guide for packaging
   system libraries
   [here](https://book.jank-lang.org/jank-build/packaging-system-lib.html).
2. If you need to build from source, try using the CMake helper that we provide.
   There's a guide for packaging source libraries [here](https://book.jank-lang.org/jank-build/packaging-source-lib.html).

From there, follow this checklist:

- When you submit your initial package, please keep the version as `0.1-SNAPSHOT`.
  jank's release tooling will assign it a correct version after merging.
- Set your package's license to match the packaged library
- Source packages must add the vendored library as a submodule under `lib`
  - Ensure that your `:verbatim-paths` includes as little as possible of the
    library
- Add a working `example` which will serve also as a test in CI; it will only be
  compiled, not executed
- Also add a PR to [awesome-jank](https://github.com/jank-lang/awesome-jank) to
  add your new library

For more details on the jank build system, be sure to read the [build system overview](https://book.jank-lang.org/jank-build/overview.html).
