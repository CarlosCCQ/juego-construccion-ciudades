Feature: Gestión de recursos y generación en el juego de ciudades

  Scenario: Crear una ciudad con recursos iniciales
    Given que no tengo ninguna ciudad creada
    When creo una ciudad llamada "CiudadPrueba1"
    Then la ciudad "CiudadPrueba1" debe tener recursos iniciales asignados
    And debe tener 100 unidades de "PIEDRA"

  Scenario: Generación automática de recursos
    Given que tengo una ciudad llamada "CiudadPrueba2" con generadores de recursos asignados
    When el tiempo programado de 30 segundos se cumple
    Then los generadores de recursos añaden más recursos a la ciudad

  Scenario: Verificar generadores asignados a una ciudad
    Given creo una ciudad "CiudadGeneradora"
    Then la ciudad debe tener 3 generadores de recursos asignados
    And debe tener un generador de tipo "CANTERAS" que genera "PIEDRA"
    And debe tener un generador de tipo "MINAS" que genera "ORO"
    And debe tener un generador de tipo "RIO" que genera "AGUA"