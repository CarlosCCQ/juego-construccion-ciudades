Feature: Gestión de ciudades con generadores de recursos y edificios

  Scenario: El jugador crea una ciudad con generadores y edificios
    Given el jugador crea una ciudad llamada "Ciudad Lima"
    When el jugador añade generadores de recursos y edificios a la ciudad
    Then los generadores de recursos producen materiales
