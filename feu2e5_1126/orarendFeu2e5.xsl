<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>
			<body>
				<h2>Órarend Gerőcs Gergő 2024/25 1. Félév</h2>

                <table border = "4">
					<tr bgcolor = "#90EE90">
						<th>ID</th>
						<th>Tipus</th>
						<th>Targy</th>
						<th colspan="2">Idopont</th>
						<th>Helyszin</th>
						<th>Oktato</th>
						<th>Szak</th>
					</tr>

                    <xsl:for-each select="GG_orarend/ora">
						<tr>
							<td><xsl:value-of select = "@id"/></td>
							<td><xsl:value-of select = "@tipus"/></td>
							<td><xsl:value-of select = "targy"/></td>
							<td><xsl:value-of select = "idopont/nap"/></td>
							<td><xsl:value-of select = "idopont/tol"/>h - <xsl:value-of select = "idopont/ig"/>h</td>
							<td><xsl:value-of select = "helyszin"/></td>
							<td><xsl:value-of select = "oktato"/></td>
							<td><xsl:value-of select = "szak"/></td>
						</tr>
					</xsl:for-each>
                </table>

            </body>
        </html>
    </xsl:template>
    <xsl:output method="html" encoding="utf-8" indent="yes"></xsl:output>

</xsl:stylesheet>
						