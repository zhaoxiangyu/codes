package org.sharp.utils.scala
import junit.framework.TestCase
import java.io.File

class ParserTest extends TestCase {
	def testParser : Unit = 
	  ArticleParsers.parse(new File("E:/xp/IV.High-speed rail in China.doc"))
	  
	def testParser2 : Unit =
	  ArticleParsers.parse(str,"test string")

	def testParser3 : Unit =
	  ArticleParsers.parse(str2,"test string")

	val str2 = """
German defence exports
Tanks for sale
A decision to sell tanks to Saudi Arabia stirs opposition 
Jul 14th 2011 | BERLIN | from the print edition 
GERMANY’S guidelines for weapons exports radiate good intentions. The buyer’s human-rights record will be given “special weight”. Arms will not be sent to countries engaged in conflict. Job creation “will play no decisive role” in deciding whether to approve a proposed deal. So what is Germany doing selling 200 state-of-the-art Leopard tanks to Saudi Arabia? 
Standing “on the side of despotism”, thundered Jürgen Trittin, co-leader of the Greens in the Bundestag. Shoring up “counterfeit stability”, snarled Volker Rühe, a former defence minister, who belongs to the Christian Democratic Union (CDU) of Chancellor Angela Merkel. As irksome as the sale itself was the manner of its disclosure. The decision by the federal security council, which meets in secret, was leaked to the press. The government still does not admit it has been made and refuses to comment directly. 
Germany is the world’s third-biggest exporter of arms. Yet it is slightly pickier than rivals over whom to sell to (nothing like France’s sale of Mistral ships to Russia). It has spurned approaches from Nepal, Thailand after a coup and Pakistan, notes Siemon Wezeman of the Stockholm International Peace Research Institute. Unlike France’s Nicolas Sarkozy, Mrs Merkel does not see herself as an arms dealer. Exports to Saudi Arabia have been rising but have not, so far, included heavy weaponry. The proposed Leopard deal is “a completely new thing”, says Mr Wezeman. 
It is part of an ongoing “de-moralising” of German foreign policy, believes Hilmar Linnenkamp of the German Institute for International and Security Affairs. The squeezing of German and European defence budgets forces arms-makers to find buyers farther afield. Already, more than half of German arms are exported. “Germany wants to be more like its allies in Europe,” says Mr Linnenkamp.
Few countries welcomed the Arab spring more loudly than Germany. The foreign minister, Guido Westerwelle, was cheered in Cairo’s Tahrir Square. Yet with the tank sale to Saudi Arabia, Germany strengthens a fundamentalist monarchy that helped squelch the stirrings of liberation in Bahrain and will not tolerate them at home. Its Leopards will reportedly be equipped to plough through cars.
This ignores Saudi Arabia’s bright side, say the (hypothetical) deal’s defenders. The country is of “great strategic importance”, says Mrs Merkel. It “does a lot against violence and terror”, adds the interior minister, Hans-Peter Friedrich. The tanks are meant to be pointed outward toward Iran, it is said, not inward at dissidents. Israel, which shares Saudi Arabia’s horror of Iran, apparently raised no objections to the sale.
Maybe, but the clumsy handling suggests disarray in German thinking. The federal security council that approved the deal is misnamed: it is a conclave of ministers headed by the chancellor that does nothing but weigh up the pros and cons of arms exports. Its deliberations are secret and its decisions are revealed only months later in an annual report. Roderich Kiesewetter, a CDU parliamentarian, admits the need for confidentiality (news that a sale had been turned down could cause a diplomatic spat), but thinks the government could do a better job of informing the Bundestag. “I’ve very rarely seen a debate with so little transparency,” he says. 
It might help if Germany had a genuine security council that tackled strategic questions. Such a body might have seen the risks in Germany’s recent decision to abstain with China and Russia rather than joining Britain and France in the UN Security Council vote authorising the use of force in Libya. “Weapons exports have to be embedded in a larger policy, which balances stability and transformation” in the Arab world, suggests Constanze Stelzenmüller of the German Marshall Fund, a think-tank. 
That would not necessarily reverse Germany’s retreat from arms-sales idealism. Export guidelines must be brought into line with reality, says Mr Linnenkamp. That means Germany and its European partners deciding how the industry should develop over the long run and how much dependence on exports is acceptable. Bad as it looks, the sale of Leopards to Saudi Arabia does not threaten the Middle East’s nascent democracy movements, Mr Linnenkamp argues. Rifles, not tanks, “are the WMD of the 21st century.”
[[noun]
guideline		despotism	Bundestag	chancellor	disclosure	mistral	coupweaponry		arm-maker	ally		fundamentalist		monarchy		stirring		ploughdissident		disarray		conclave		deliberation	parliamentarian	confidentialityspat		think-tank	idealism
]

[[verb]
stir		radiate	thunder	snarl		spurn	demoralize	squeeze		cheersquelch		misname		abstain
]

[[adj]
irksome		picky	hypothetical		clumsy		diplomatic	nascent
]

[[adv]
reportedly	outwards		inward
]
"""
	val str =  """
The prime minister, Wen Jiabao, has been forthright, at least in admitting the extent of the problem. At a news conference last month, he said that corruption had been getting “more and more serious”, a departure from the party's usual line that its clean-up is having some success. Mr Wen must have bitten his tongue. In the official transcript published by the Chinese press, his remarks were more cautious: corruption was only “quite serious” in some places and departments. The media did report his unoriginal but far-reaching suggestion that one remedy would be more political reform. But there is little sign that Chinese leaders are eager to pursue that. 

[[noun]
crackdown	wrongdoing	rhetoric	remit	signatory	whim	commission
sprinkle	princeling	decree	rival	lottery	facility	subsidiary
placeman	echelon	departure	politburo transcript 	remedy
]

[[verb]
espouse	jockey		topple		allege		authoritarian		portray
enforce	divert		stoke
]

[[adj]
sleazy		notably	patchy		combative	forthright		unoriginal
far-reaching
]
"""
}