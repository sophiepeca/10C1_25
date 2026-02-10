# Fenster, Szenen und Objekte

## Das Fenster ([czg.MainWindow](../../src/main/java/czg/MainWindow.java))

Die `MainWindow`-Klasse stellt eine einzige Instanz über `MainWindow.INSTANCE`
bereit. Über diese kann auf Funktionen der Standard-Java-Klasse `JFrame` zugegriffen
werden, z.B. `getMousePosition()`.
Die Dokumentation von `JFrame` ist [**hier**](https://docs.oracle.com/javase/8/docs/api/javax/swing/JFrame.html)
zu finden.

Weiterhin finden sich in dieser Klasse folgende statische Konstanten:

- `PIXEL_SCALE`: Wie groß ein einzelnes Pixel einer Bilddatei dargestellt werden
  soll. Aktuell beträgt der Wert `6`. Ein Pixel eines Bildes wird also als 6x6
  Pixel dargestellt.
- `WIDTH`: Breite des Fensters in Pixeln
- `HEIGHT`: Höhe des Fensters in Pixeln
- `FPS`: ("Frames Per Second") Wie oft in einer Sekunde die Spiellogik ausgeführt
  und die Grafik gezeichnet wird

**Achtung:** Aktuell ist für die Hintergründe eine Auflösung von **140 mal 105 Pixeln**
vorgesehen.

### `main()`-Funktion & Erste Szene

Die `main()`-Funktion des Spiels befindet sich in der `MainWindow`-Klasse. In ihr
wird die Logik-Schleife des Spiels gestartet und **die erste Szene hinzugefügt**.
Hier sollte also eine Instanz der ersten Szene (z.B. Hauptmenü) erstellt werden,
die Spielfigur zu dieser hinzugefügt werden und schließlich die Szene auf den
Szenen-Stapel gelegt werden.

Beispiel (angenommen `GangScene` ist die Klasse für die Start-Szene):

```java
GangScene start = new GangScene();
start.objects.add(Player.INSTANCE);
SceneStack.INSTANCE.push(start);
```

Diese Szene könnte dann Pfeile oder Türen enthalten, die zu anderen Szenen führen.
Analog könnte die erste Szene auch ein Menü sein, welches zu anderen Szenen führt.


## Szene ([czg.scenes.BaseScene](../../src/main/java/czg/scenes/BaseScene.java))

Eine Szene enthält eine Liste von Objekten. Wenn die Szene gezeichnet (`draw()`-Methode)
oder ihre Logik durchlaufen (`update()`-Methode) wird, führt sie wiederum die `draw()`- bzw.
`update()`-Methoden aller ihrer Objekte aus.

**Achtung:** `BaseScene.update()` fertigt zunächst eine **Kopie** der Objekt-Liste an, über
welche dann iteriert wird. So kann die `objects`-Liste in den `update()`-Methoden der Objekte
geändert werden, ohne dass es zu einem Fehler kommt - Listen können nicht geändert werden, während
an einer anderen Stelle über sie iteriert wird - jedoch werden die Änderungen von `BaseScene.update()`
erst beim nächsten Durchlauf berücksichtigt.

Die `BaseScene`-Klasse ist **abstrakt**. Um eigene Szenen zu erstellen, muss also eine neue Klasse
erstellt werden, welche die `BaseScene`-Klasse erweitert (`class ... extends BaseScene`). Diese
sollte dann in ihrem Konstruktor ihre Objekte erstellen und hinzufügen, und *kann* auch die
`draw()`- und `update()`-Methoden überschreiben bzw. erweitern (überschreiben, aber mit `super.draw()`
bzw. `super.update()`) die vorgefertigten Methoden von `BaseScene` aufrufen.


## Der Szenenstapel ([czg.scenes.SceneStack](../../src/main/java/czg/scenes/SceneStack.java))

Der Szenenstapel ist der einzige Inhalt des Fensters. Er speichert eine Liste
von allen Szenen auf dem Bildschirm. Die erste Szene, die hinzugefügt wird,
führt als erste ihre `update()`- und `draw()`-Methoden aus.

Die **Instanz des Szenenstapels** wird über die statische Konstante `SceneStack.INSTANCE`
bereitgestellt.

Um Szenen hinzuzufügen und zu entfernen, können die Methoden `push()`, `pop()`,
`insert()`, `replace()` und `remove()` in der `SceneStack`-Klasse verwendet werden.

**Achtung:** Analog zu Szenen arbeitet der Szenenstapel in seiner `update()`-Methode mit einer
Kopie seiner Liste von Objekten. Das Hinzufügen und Entfernen von Szenen wird also
erst beim nächsten durchlauf von `SceneStack.update()` berücksichtigt.


## Objekte ([czg.objects.BaseObject](../../src/main/java/czg/objects/BaseObject.java))

Die **abstrakte** `BaseObject`-Klasse stellt die folgenden Variablen bereit:

- `int x`: X-Koordinate \*
- `int y`: Y-Koordinate \*
- `int width`: Breite \*
- `int height`: Höhe \*

\* In Pixeln. Wird am Ende mithilfe von `MainWindow.PIXEL_SCALE` hochskaliert.

- `Image sprite`: Bild des Objekts


Weiterhin sind die folgenden Methoden enthalten:

- `Rectangle2D getHitbox()`: Gibt die Position und Größe des Objektes zurück,
  verpackt in eine `Rectangle2D`-Objekt. Siehe [**hier**](https://docs.oracle.com/javase/8/docs/api/java/awt/geom/Rectangle2D.html)
  für die Dokumentation von `Rectangle2D`.
- `boolean isClicked()`: Gibt zurück, ob sich die Maus auf der Spielfigur befindet
  und die linke Maustaste gedrückt wird.


Zuletzt gibt es die folgenden Konstruktoren. Diese sind  als `protected` markiert,
können also nur von **Unterklassen von BaseObject** verwendet werden.

- `BaseObject(Image sprite)`
- `BaseObject(Image sprite, int x, int y)`
- `BaseObject(Image sprite, int x, int y, int width, int height)`

Werden `x` und `y` nicht angegeben, wird das Objekt in die Mitte des Bildschirms platziert.

Werden `width` und `height` nicht angegeben, werden sie auf die Breite und Höhe von `sprite`
**multipliziert mit `MainWindow.PIXEL_SCALE`** gesetzt.


### `draw()` und `update()`

`BaseObject.update()` ist **abstrakt** und muss deshalb immer von den Unterklassen implementiert
werden.

Die vorgefertigte `draw()`-Methode zeichnet `sprite` an den Koordinaten `(x|y)` mit
den Dimensionen `width x height`. Sie *kann* von Unterklassen von `BaseObjekt` überschrieben
bzw. erweitert werden.
