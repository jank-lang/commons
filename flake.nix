{
  description = "Dev environment for jank commons.";

  inputs = {
    flake-parts.url = "github:hercules-ci/flake-parts";
    nixpkgs.url = "github:NixOS/nixpkgs/nixpkgs-unstable";
    self.submodules = true;
  };

  outputs = inputs @ {flake-parts, ...}:
    flake-parts.lib.mkFlake {inherit inputs;} {
      systems = ["x86_64-linux" "aarch64-linux" "aarch64-darwin" "x86_64-darwin"];
      perSystem = {
        self',
        pkgs,
        lib,
        ...
      }: let
      in {
        legacyPackages = pkgs;
        formatter = pkgs.alejandra;

        devShells.default = pkgs.mkShell {
          packages = with pkgs; [
            ## Build tools.
            cmake
            ninja
            pkg-config
            leiningen
            babashka

            # Linting.
            shellcheck
          ];
        };
      };
    };
}
