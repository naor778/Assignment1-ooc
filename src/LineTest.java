import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LineTest {

    @org.junit.jupiter.api.Test
    void length() {
        // 1️⃣ קו אלכסוני רגיל (3-4-5)
        Line l1 = new Line(0, 0, 3, 4);
        assertEquals(5.0, l1.length(), 1e-9, "צפוי אורך 5 לפי פיתגורס");

        // 2️⃣ קו אופקי (y קבוע)
        Line l2 = new Line(0, 2, 10, 2);
        assertEquals(10.0, l2.length(), 1e-9, "קו אופקי באורך 10");

        // 3️⃣ קו אנכי (x קבוע)
        Line l3 = new Line(5, 0, 5, 6);
        assertEquals(6.0, l3.length(), 1e-9, "קו אנכי באורך 6");

        // 4️⃣ קו באורך אפס (שתי נקודות זהות)
        Line l4 = new Line(2, 2, 2, 2);
        assertEquals(0.0, l4.length(), 1e-9, "קו מנקודה לנקודה זהה – אורך 0");

        // 5️⃣ קו עם קואורדינטות שליליות
        Line l5 = new Line(-1, -1, 2, 3);
        double expected5 = Math.sqrt(Math.pow(2 - (-1), 2) + Math.pow(3 - (-1), 2)); // √((3)^2 + (4)^2) = 5
        assertEquals(expected5, l5.length(), 1e-9, "קו מ-(−1,−1) עד (2,3) אורך 5");

        // 6️⃣ קו בכיוון הפוך (אמור להחזיר אותו אורך)
        Line l6 = new Line(3, 4, 0, 0);
        assertEquals(5.0, l6.length(), 1e-9, "כיוון הפוך עדיין אורך 5");
    }

    @org.junit.jupiter.api.Test
    void middle() {
        // 1️⃣ קו רגיל באלכסון
        Line l1 = new Line(0, 0, 4, 4);
        Point mid1 = l1.middle();
        assertEquals(2.0, mid1.getX(), 1e-9, "X של האמצע אמור להיות 2");
        assertEquals(2.0, mid1.getY(), 1e-9, "Y של האמצע אמור להיות 2");

        // 2️⃣ קו אופקי
        Line l2 = new Line(0, 10, 10, 10);
        Point mid2 = l2.middle();
        assertEquals(5.0, mid2.getX(), 1e-9, "קו אופקי — X האמצעי הוא 5");
        assertEquals(10.0, mid2.getY(), 1e-9, "Y קבוע 10");

        // 3️⃣ קו אנכי
        Line l3 = new Line(5, 0, 5, 10);
        Point mid3 = l3.middle();
        assertEquals(5.0, mid3.getX(), 1e-9, "קו אנכי — X קבוע 5");
        assertEquals(5.0, mid3.getY(), 1e-9, "Y באמצע הוא 5");

        // 4️⃣ קו עם קואורדינטות שליליות
        Line l4 = new Line(-2, -2, 2, 2);
        Point mid4 = l4.middle();
        assertEquals(0.0, mid4.getX(), 1e-9, "קו מ-(-2,-2) עד (2,2) — X באמצע 0");
        assertEquals(0.0, mid4.getY(), 1e-9, "Y באמצע 0");

        // 5️⃣ קו בכיוון הפוך (מתחיל מנקודה גדולה יותר)
        Line l5 = new Line(10, 0, 0, 10);
        Point mid5 = l5.middle();
        assertEquals(5.0, mid5.getX(), 1e-9, "גם בכיוון הפוך האמצע X=5");
        assertEquals(5.0, mid5.getY(), 1e-9, "גם בכיוון הפוך האמצע Y=5");

        // 6️⃣ שתי נקודות זהות (קו מאורך אפס)
        Line l6 = new Line(3, 3, 3, 3);
        Point mid6 = l6.middle();
        assertEquals(3.0, mid6.getX(), 1e-9, "אם שתי הנקודות זהות — האמצע הוא בדיוק אותה נקודה");
        assertEquals(3.0, mid6.getY(), 1e-9, "אותו דבר בציר Y");
    }

    @org.junit.jupiter.api.Test
    void slope() {
        Line horizontal = new Line(0, 5, 10, 5);
        assertEquals(0.0, horizontal.slope(), 1e-9, "קו אופקי צריך שיפוע 0");

        // 2️⃣ קו עולה מימין לשמאל (y גדל כשה-x קטן) → slope חיובי
        Line rising = new Line(0, 0, 2, 2);
        assertEquals(1.0, rising.slope(), 1e-9, "קו (0,0)-(2,2) שיפוע 1");

        // 3️⃣ קו יורד (y קטן כשה-x גדל) → slope שלילי
        Line falling = new Line(0, 4, 4, 0);
        assertEquals(-1.0, falling.slope(), 1e-9, "קו יורד (0,4)-(4,0) שיפוע -1");

        // 4️⃣ קו אנכי (x קבוע) → צריך להחזיר null
        Line vertical = new Line(3, 0, 3, 5);
        assertNull(vertical.slope(), "קו אנכי צריך להחזיר null");

        // 5️⃣ קו הפוך של אופקי – עדיין שיפוע 0
        Line horizontalReverse = new Line(10, 5, 0, 5);
        assertEquals(0.0, horizontalReverse.slope(), 1e-9, "קו אופקי בכיוון הפוך עדיין slope=0");

        // 6️⃣ קו באותו מקום (שתי נקודות זהות)
        Line samePoint = new Line(1, 1, 1, 1);
        assertNull(samePoint.slope(), "קו מאותה נקודה – אנכי או חסר משמעות → null");

    }

    @org.junit.jupiter.api.Test
    void isIntersecting() {
        Line l1 = new Line(0, 0, 4, 4);
        Line l2 = new Line(0, 4, 4, 0);
        assertTrue(l1.isIntersecting(l2), "אמור להיחתך (X במרכז)");

        // 2️⃣ קווים מקבילים (לא נחתכים)
        Line l3 = new Line(0, 0, 4, 0);
        Line l4 = new Line(0, 1, 4, 1);
        assertFalse(l3.isIntersecting(l4), "מקבילים – לא נחתכים");

        // 3️⃣ קווים אנכיים ונחתכים
        Line l5 = new Line(1, -1, 1, 3); // אנכי
        Line l6 = new Line(0, 1, 3, 1);  // אופקי
        assertTrue(l5.isIntersecting(l6), "אמור להיחתך בנקודה (1,1)");

        // 4️⃣ קווים חופפים (על אותו ישר, חלקם משותף)
        Line l7 = new Line(0, 0, 4, 0);
        Line l8 = new Line(2, 0, 6, 0);
        assertTrue(l7.isIntersecting(l8), "חופפים חלקית – נחשבים כנחתכים");

        // 5️⃣ קווים עם נקודת קצה משותפת בלבד
        Line l9  = new Line(0, 0, 2, 2);
        Line l10 = new Line(2, 2, 3, 4);
        assertTrue(l9.isIntersecting(l10), "נפגשים בדיוק בקצה (2,2)");

        // 6️⃣ קווים אנכיים באותו X אך ללא חפיפה
        Line l11 = new Line(1, 0, 1, 2);
        Line l12 = new Line(1, 3, 1, 5);
        assertFalse(l11.isIntersecting(l12), "אנכיים באותו X אך לא חופפים – לא נחתכים");

        // 7️⃣ קווים נוטים שלא נחתכים (שיפועים שונים אך תחומים מופרדים)
        Line l13 = new Line(0, 0, 1, 1);
        Line l14 = new Line(2, 0, 3, 1);
        assertFalse(l13.isIntersecting(l14), "שיפועים שונים אך הקווים רחוקים – לא נחתכים");
    }


    @org.junit.jupiter.api.Test
    void intersectionWith() {
        Line l1 = new Line(0,0,2,0);
        Line l2 = new Line(1,-1,1,1);
        Point p = l1.intersectionWith(l2);

        assertNotNull(p);
        assertEquals(1.0, p.getX(), 1e-9);
        assertEquals(0.0, p.getY(), 1e-9);
    }


    @org.junit.jupiter.api.Test
    void testEquals() {
        Line test1=new Line(0,0,0,0);
        Line test2=new Line(0,0,0,0);
        Line test3=new Line(1,0,0,0);
        Line test4=new Line(1,1,0,0);

        assertTrue((test1.equals(test2)));
      assertFalse(test1.equals(test3));
      assertFalse(test2.equals(test3));
      assertFalse(test3.equals(test4));
    }

    @org.junit.jupiter.api.Test
    void inRange() {
        Line line = new Line(0, 0, 1, 1); // לא משנה איזה קו — רק בשביל לקרוא ל-inRange

        // 1️⃣ ערך שנמצא באמצע התחום
        assertTrue(line.inRange(0, 10, 5), "5 נמצא בין 0 ל-10 → צריך להיות true");

        // 2️⃣ ערך בדיוק על הגבול התחתון
        assertTrue(line.inRange(0, 10, 0), "0 נמצא בגבול התחתון → צריך להיות true");

        // 3️⃣ ערך בדיוק על הגבול העליון
        assertTrue(line.inRange(0, 10, 10), "10 נמצא בגבול העליון → צריך להיות true");

        // 4️⃣ ערך קטן מהתחום
        assertFalse(line.inRange(0, 10, -1), "-1 קטן מ-0 → צריך להיות false");

        // 5️⃣ ערך גדול מהתחום
        assertFalse(line.inRange(0, 10, 11), "11 גדול מ-10 → צריך להיות false");

        // 6️⃣ ערך באמצע גם כשהסדר הפוך (a > b)
        assertTrue(line.inRange(10, 0, 5), "5 עדיין בין 10 ל-0 (הפוך) → true");

        // 7️⃣ ערך מחוץ לתחום כשהסדר הפוך
        assertFalse(line.inRange(10, 0, 11), "11 מחוץ לטווח גם כשהסדר הפוך → false");

        // 8️⃣ ערכים שווים (a == b)
        assertTrue(line.inRange(5, 5, 5), "a=b=5 → רק ערך שווה בדיוק אמור להחזיר true");
        assertFalse(line.inRange(5, 5, 4.999), "אם isIn ≠ 5 → false");
    }
    @Test
    void overlappingDiagonalLines() {
        Line a = new Line(0,0, 4,4);
        Line b = new Line(2,2, 6,6); // חופף חלקית
        assertTrue(a.isIntersecting(b));
        assertNotNull(a.intersectionWith(b)); // אצלך מחזיר נקודת חפיפה כלשהי
    }
    @Test
    void parallelDiagonalLinesNoIntersection() {
        Line a = new Line(0,0, 4,4);   // y=x
        Line b = new Line(0,1, 4,5);   // y=x+1
        assertFalse(a.isIntersecting(b));
        assertNull(a.intersectionWith(b));
    }
    @Test
    void intersectionAtEndpoint() {
        Line a = new Line(0,0, 4,0);
        Line b = new Line(4,0, 4,5);
        assertTrue(a.isIntersecting(b));
        Point p = a.intersectionWith(b);
        assertEquals(4.0, p.getX(), 1e-9);
        assertEquals(0.0, p.getY(), 1e-9);
    }
    @Test
    void zeroLengthLineIntersection() {
        Line dot = new Line(1,1, 1,1);
        Line a   = new Line(0,1, 3,1);
        assertTrue(dot.isIntersecting(a)); // אם נקודה יושבת על הקו
        assertNotNull(dot.intersectionWith(a));
    }
    @Test
    void closestIntersection_NoIntersection() {
        Line line = new Line(0, 0, -5, -5);
        Rectangle rect = new Rectangle(new Point(10, 10), 20, 20);

        assertNull(line.closestIntersectionToStartOfLine(rect),
                "אין חיתוך — צריך להחזיר null");
    }

    @Test
    void closestIntersection_SingleIntersection() {
        Line line = new Line(0, 0, 20, 20);
        Rectangle rect = new Rectangle(new Point(10, 10), 10, 10);

        Point p = line.closestIntersectionToStartOfLine(rect);

        assertNotNull(p, "יש חיתוך — אסור להחזיר null");
        assertEquals(10.0, p.getX(), 1e-9);
        assertEquals(10.0, p.getY(), 1e-9);
    }

    @Test
    void closestIntersection_TwoIntersectionsChooseClosest() {
        Line line = new Line(0, 0, 30, 10);
        Rectangle rect = new Rectangle(new Point(10, 0), 10, 10);

        // הצלעות נותנות שתי נקודות חיתוך:
        // (10, ~3.33) ו (20, ~6.66)
        Point p = line.closestIntersectionToStartOfLine(rect);

        assertNotNull(p);
        // בודקים שהוא בחר את הראשונה (הקרובה ל־start)
        assertEquals(10.0, p.getX(), 1e-9);
    }

    @Test
    void closestIntersection_HitAtEdge() {
        Line line = new Line(0, 0, 10, 0);
        Rectangle rect = new Rectangle(new Point(10, -5), 10, 10);

        Point p = line.closestIntersectionToStartOfLine(rect);

        assertNotNull(p);
        assertEquals(10.0, p.getX(), 1e-9);
        assertEquals(0.0, p.getY(), 1e-9);
    }

    @Test
    void closestIntersection_DiagonalRectangleHit() {
        Line line = new Line(0, 0, 15, 15);
        Rectangle rect = new Rectangle(new Point(5, 5), 10, 10);

        Point p = line.closestIntersectionToStartOfLine(rect);

        // נקודת הפגיעה הראשונה = (5,5)
        assertNotNull(p);
        assertEquals(5.0, p.getX(), 1e-9);
        assertEquals(5.0, p.getY(), 1e-9);
    }



}