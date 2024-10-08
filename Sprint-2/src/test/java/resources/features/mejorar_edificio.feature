Feature: Mejorar edificio
  Scenario: Como jugador quiero que los edificios se puedan mejorar si tengo suficientes recursos para poder optimizar el desarrollo de mi ciudad
    Given el jugador tiene un edificio de tipo "CLASE_BAJA"
    And el jugador tiene suficientes recursos para mejorar
    When el sistema verifica los recursos disponibles
    Then el edificio se mejora al siguiente nivel o tipo