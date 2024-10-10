Feature: Gestión de recursos y generación en el juego de ciudades

  Background:
    Given que no tengo ninguna ciudad creada

  Scenario: Crear una ciudad con recursos iniciales
    When creo una ciudad llamada "CiudadPrueba"
    Then la ciudad "CiudadPrueba" debe tener recursos iniciales asignados
    And debe tener 100 unidades de "PIEDRA"

  Scenario: Generar recursos manualmente
    Given el jugador tiene generadores de recursos asignados a su ciudad
    When el jugador decide generar recursos manualmente
    Then los recursos se añaden a los recursos actuales de la ciudad

  Scenario: Generación automática de recursos
    Given una ciudad tiene generadores de recursos activos
    When el tiempo programado de generación de recursos se cumple
    Then los generadores de recursos añaden más recursos a la ciudad

  Scenario: Verificar generadores asignados a una ciudad
    Given creo una ciudad llamada "CiudadGeneradora"
    Then la ciudad "CiudadGeneradora" debe tener 3 generadores de recursos asignados
    And debe tener un generador de tipo "CANTERAS" que genera "PIEDRA"
    And debe tener un generador de tipo "MINAS" que genera "ORO"
    And debe tener un generador de tipo "RIO" que genera "AGUA"