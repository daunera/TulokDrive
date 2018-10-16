<html>
	<body>
		<table cellspacing="0" cellpadding="0" border="0" style="color: #333; background: #fff; padding: 0; margin: 0; width: 100%; font: 15px 'Helvetica Neue', Arial, Helvetica;">
			<tr width="100%">
				<td align="top" align="left" style="background: #f0f0f0; font: 15px 'Helvetica Neue', Arial, Helvetica;">
					<table style="border: none; padding: 0 18px; margin: 50px auto; width: 500px;">
						<tr width="100%" height="60">
							<td valign="top" align="left" style="border-top-left-radius: 10px; border-top-right-radius: 10px; background: #65737e; padding: 12px 18px; text-align: center;">
								<img height="40" src="http://varosmajor.cserkesz.hu/wp-content/uploads/2018/07/navlogo.png" title="Városmajori cserkészek" style="font-weight: bold; font-size: 18px; color: #fff; vertical-align: top;">
							</td>
						</tr>
						<tr width="100%">
							<td valign="top" align="left" style="border-bottom-left-radius: 10px; border-bottom-right-radius: 10px; background:#fff; padding: 18px 16px;">
								<h1 style=\"margin-top: 0;\">${mail.title}</h1>
								<p> Kedves ${mail.toName}! </p>
								<#list mail.lines as line>
								<p>${line}</p>
								</#list>
								<p align="center">
									<a href="${mail.link}" style="-webkit-appearance: none; border-radius: 5px; -moz-border-radius: 5px; -webkit-border-radius: 5px; background: #343d46; border: 1px solid #666666; color: #fff; cursor: pointer; display: inline-block; font-weight: 700; line-height: 20px; margin: 10px 0px; padding: 10px 50px; text-decoration: none;"> Irány a honlap! </a>
								</p> 
								<p style="margin-bottom: 10px;"> Szép napot! <br> &#x1F984; MuuuBot </p>
							</td>
						</tr>
						<tr>
							<td align="center" style="padding: 10px 16px; color: #a7a7a7; font-size: 85%\">
								Valami gond van? <a href="mailto:dauner.agoston+tulokdrive@cserkesz.hu?subject=TulokDrive+visszajelzés" style="color: inherit;">Írj emailt!</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>