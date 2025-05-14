use strict;
use warnings;

use Glass qw(isEmpty);
use Guy::User qw(work drink);
use Guy::Intern qw(fillGlass);

my $timeOfTheDay = 0;
my $user = User->new();
my $glass = Glass->new();

while ($timeOfTheDay < 480) {
	# Writing this line is the whole purpose of this script
	unless(!$glass->isEmpty()){
		my $intern = Intern->new();
		$intern->fillGlass($glass);
	}
	$user->drink($glass);
    sleep(60);
    $timeOfTheDay++;
}
