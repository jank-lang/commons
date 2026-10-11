{
  description = "Dev environment for jank commons.";

  inputs = {
    flake-parts.url = "github:hercules-ci/flake-parts";
    nixpkgs.url = "github:NixOS/nixpkgs/nixpkgs-unstable";
    jank.url = "git+https://github.com/jank-lang/jank?submodules=1";
    self.submodules = true;
  };

  outputs = inputs @ {flake-parts, ...}:
    flake-parts.lib.mkFlake {inherit inputs;} {
      systems = [
        "x86_64-linux"
        "aarch64-linux"
        "aarch64-darwin"
        "x86_64-darwin"
      ];
      perSystem = {
        pkgs,
        inputs',
        ...
      }: let
        # Commons builds these packages, so include their transitive
        # dependencies.
        commonsTransitive = with pkgs; [
          box2d
          ftxui
          glm
          imgui
          raygui
          raylib
          sdl3
        ];
        # Commons depends on these packages directly (probably via pkg-config).
        commonsDirect = with pkgs; [
          libGL
          glfw3
          ncurses
          sqlite
        ];
      in {
        legacyPackages = pkgs;
        formatter = pkgs.alejandra;

        devShells.default = pkgs.mkShell {
          inputsFrom = commonsTransitive;
          packages = with pkgs;
            [
              ## Jank
              inputs'.jank.packages.default

              ## Build tools.
              cmake
              ninja
              pkg-config
              leiningen
              babashka
              bubblewrap

              ## Linting.
              clj-kondo
              shellcheck
            ]
            ++ commonsDirect;
        };
      };
    };
}
