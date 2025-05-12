use std::{time::Duration, thread};


//=================================//
//      Definitions - Glass        //
//=================================//

enum DrinkType {
    Water,
    Juice,
    Milk,
    Tea,
    Coffe,
    Soda,
    Smoothie,
    HotChocolate,
    Monster,
}
struct Glass {
    drink: DrinkType,
    cur_volume: f32,
    max_volume: f32,
}
impl Glass {
    fn empty(&self) -> bool {
        self.cur_volume <= 0.0
    }
}


//=================================//
//      Definitions - Intern       //
//=================================//

struct Intern {}
impl Intern {
    fn fill_glass(&self, glass: &mut Glass) {
        glass.cur_volume = glass.max_volume;
    }
}


//=================================//
//      Definitions - User         //
//=================================//

struct User {
    is_thirsty: bool,
    worked_hours: u8,
}
impl User {
    fn drink(&mut self, glass: &mut Glass) {
        let temp = glass.cur_volume - 10.0;
        glass.cur_volume = f32::max(temp, 0.0);
        self.is_thirsty = false;
    }

    fn work(&mut self) {
        thread::sleep(Duration::from_secs(60*60));
        self.worked_hours += 1;
        self.is_thirsty = true;
    }
}


//=================================//
//         Main-Routine            //
//=================================//

fn main() {
    let mut user  = User {is_thirsty: true, worked_hours: 0};
    let mut glass = Glass{drink: DrinkType::Water, cur_volume: 0.0, max_volume: 100.0};

    while user.worked_hours < 8 {
        let empty   = glass.empty();
        let thirsty = user.is_thirsty;

        if !empty && thirsty {
            user.drink(&mut glass);
        }

        else if empty && thirsty {
            let intern = Intern{};
            intern.fill_glass(&mut glass);
            user.drink(&mut glass);
        }

        user.work();
    }
}
