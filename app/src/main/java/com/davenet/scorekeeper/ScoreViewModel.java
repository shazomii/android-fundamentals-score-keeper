package com.davenet.scorekeeper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ScoreViewModel extends ViewModel {
    private MutableLiveData<Integer> _score1 = new MutableLiveData<>(0);

    public LiveData<Integer> score1() {
        return _score1;
    }

    private MutableLiveData<Integer> _score2 = new MutableLiveData<>(0);

    public LiveData<Integer> score2() {
        return _score2;
    }

    private MutableLiveData<Boolean> _teamOneWin = new MutableLiveData<>();

    public LiveData<Boolean> teamOneWin() {
        return _teamOneWin;
    }

    private MutableLiveData<Boolean> _teamTwoWin = new MutableLiveData<>();

    public LiveData<Boolean> teamTwoWin() {
        return _teamTwoWin;
    }

    private MutableLiveData<Boolean> _eventOneAhead = new MutableLiveData<>();

    public LiveData<Boolean> eventOneAhead() {
        return _eventOneAhead;
    }

    private MutableLiveData<Boolean> _eventTwoAhead = new MutableLiveData<>();

    public LiveData<Boolean> eventTwoAhead() {
        return _eventTwoAhead;
    }

    private MutableLiveData<Boolean> _eventScoreEqual = new MutableLiveData<>();

    public LiveData<Boolean> eventScoreEqual() {
        return _eventScoreEqual;
    }

    private MutableLiveData<Boolean> _eventPlayAgain = new MutableLiveData<>();

    public LiveData<Boolean> eventPlayAgain() {
        return _eventPlayAgain;
    }

    public void decreaseTeam1() {
        if (_score1.getValue() != null && _score2.getValue() != null && _score1.getValue() != 0) {
            _score1.setValue(_score1.getValue() - 1);
            if (_score1.getValue() > _score2.getValue()) {
                onTeamOneAhead();
            } else if (_score1.getValue().equals(_score2.getValue())) {
                onScoreEqual();
            } else {
                onTeamTwoAhead();
            }
        }

    }

    public void decreaseTeam2() {
        if (_score2.getValue() != null && _score1.getValue() != null && _score2.getValue() != 0) {
               _score2.setValue(_score2.getValue() - 1);
               if (_score2.getValue() > _score1.getValue()) {
                   onTeamTwoAhead();
               } else if (_score1.getValue().equals(_score2.getValue())) {
                   onScoreEqual();
               } else {
                   onTeamOneAhead();
               }
        }
    }

    public void increaseTeam1() {
        if (_score1.getValue() != null && _score2.getValue() != null) {
            if (_score1.getValue() == 9) {
                onTeamOneWin();
            } else {
                _score1.setValue(_score1.getValue() + 1);
                if (_score1.getValue() > _score2.getValue()) {
                    onTeamOneAhead();
                } else if (_score1.getValue().equals(_score2.getValue())) {
                    onScoreEqual();
                } else {
                    onTeamTwoAhead();
                }
            }
        }
    }

    public void increaseTeam2() {
        if (_score2.getValue() != null && _score1.getValue() != null) {
            if (_score2.getValue() == 9) {
                onTeamTwoWin();
            } else {
                _score2.setValue(_score2.getValue() + 1);
                if (_score2.getValue() > _score1.getValue()) {
                    onTeamTwoAhead();
                } else if (_score1.getValue().equals(_score2.getValue())) {
                    onScoreEqual();
                } else {
                    onTeamOneAhead();
                }
            }

        }
    }

    private void onTeamTwoWin() {
        _teamTwoWin.setValue(true);
    }

    private void onTeamOneWin() {
        _teamOneWin.setValue(true);
    }

    private void onTeamOneAhead() {
        _eventOneAhead.setValue(true);
    }

    private void onTeamTwoAhead() {
        _eventTwoAhead.setValue(true);
    }

    private void onScoreEqual() {
        _eventScoreEqual.setValue(true);
        _eventOneAhead.setValue(false);
        _eventTwoAhead.setValue(false);
    }

    public void resetGame() {
        _score1.setValue(0);
        _score2.setValue(0);
        _teamOneWin.setValue(false);
        _teamTwoWin.setValue(false);
        _eventPlayAgain.setValue(true);
        _eventOneAhead.setValue(false);
        _eventTwoAhead.setValue(false);
        _eventScoreEqual.setValue(true);
    }

    public void onResetGameComplete() {
        _eventPlayAgain.setValue(false);
    }
}


