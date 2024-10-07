Feature: Crear una ciudad con edificios y generadores de recursos

  Scenario: El jugador crea una ciudad con edificios y generadores de recursos
    Given el jugador está creando una nueva ciudad
    When el jugador añade generadores de recursos y edificios a la ciudad
    Then los generadores de recursos producen materiales
