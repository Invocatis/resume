{
  inputs = {
  };

  outputs =
    {
      self,
      nixpkgs,
      ...
    }:
    let
      inherit (nixpkgs) lib;
      eachSystem = f: lib.genAttrs nixpkgs.lib.systems.flakeExposed (system: f nixpkgs.legacyPackages.${system});
    in
    {
      packages = eachSystem (pkgs: {});

      devShells = eachSystem (pkgs: {
        default = pkgs.mkShell {
          packages = [
            pkgs.pandoc
            pkgs.fswatch
            pkgs.babashka
          ];
        };
      });
    };
}
