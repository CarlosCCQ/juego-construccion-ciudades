Feature:
  Scenario: Como jugador quiero construir diferentes tipos de edificios para desarrollar mi ciudad con una variedad de infraestructuras.

    Given el jugador tiene suficientes recursos
    When el jugador selecciona un tipo de edificio para construir
    Then el edificio seleccionado se añade a la ciudad del jugador
    And los recursos necesarios para construir el edificio se descuentan del inventario del jugador
